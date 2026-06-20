package com.chriscodelab.contact.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chriscodelab.contact.dto.request.ContactRequestDTO;
import com.chriscodelab.contact.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(30);

    private final String apiKey;
    private final String apiUrl;
    private final String senderEmail;
    private final String senderName;
    private final boolean debug;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public EmailServiceImpl(
            @Value("${brevo.api.key}") String apiKey,
            @Value("${brevo.api.url}") String apiUrl,
            @Value("${brevo.api.sender-email}") String senderEmail,
            @Value("${brevo.api.sender-name}") String senderName,
            @Value("${brevo.debug:false}") boolean debug) {
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.senderEmail = senderEmail;
        this.senderName = senderName;
        this.debug = debug;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void sendContactEmails(ContactRequestDTO request) {
        LOGGER.info("Sending Brevo emails: user={}, apiUrl={}, debug={}", request.getEmail(), apiUrl, debug);
        if (debug) {
            LOGGER.info("Brevo sender configured as {} <{}>", senderName, senderEmail);
        }
        sendConfirmationEmail(request);
        sendOwnerNotificationEmail(request);
    }

    private void sendConfirmationEmail(ContactRequestDTO request) {
        LOGGER.debug("Preparing confirmation email to {}", request.getEmail());
        Map<String, Object> payload = Map.of(
                "sender", Map.of("name", senderName, "email", senderEmail),
                "to", List.of(Map.of("email", request.getEmail(), "name", request.getName())),
                "subject", "Thank you for contacting ChrisCodeLab",
                "textContent", String.format("Hello %s,%n%nThank you for reaching out to me. I will get back to you as soon as I can.%n%nBest regards,%nChrisCodeLab", request.getName()),
                "htmlContent", String.format("<p>Hello %s,</p><p>Thank you for reaching out to me. I will get back to you as soon as I can.</p><p>Best regards,<br/>ChrisCodeLab</p>", request.getName()));

        sendEmail(payload);
    }

    private void sendOwnerNotificationEmail(ContactRequestDTO request) {
        // Owner recipient is the submitter's email (dynamic)
        String recipient = request.getEmail();
        LOGGER.debug("Preparing owner notification email to {}", recipient);
        Map<String, Object> payload = Map.of(
                "sender", Map.of("name", senderName, "email", senderEmail),
                "to", List.of(Map.of("email", recipient, "name", request.getName())),
                "subject", "chriscodelab: New contact message from " + request.getName(),
                "textContent", String.format("You have received a message from %s <%s>.%n%nMessage:%n%s%n%nThis is an automated notification from your contact-service.", request.getName(), request.getEmail(), request.getMessage()),
                "htmlContent", String.format("<p>You have received a message from <strong>%s</strong> &lt;%s&gt;.</p><p><strong>Message:</strong></p><p>%s</p><p>This is an automated notification from your contact-service.</p>", request.getName(), request.getEmail(), request.getMessage()));

        sendEmail(payload);
    }

    private void sendEmail(Map<String, Object> payload) {
        try {
            String body = objectMapper.writeValueAsString(payload);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .timeout(REQUEST_TIMEOUT)
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .header("api-key", apiKey)
                    .POST(BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                LOGGER.error("Brevo email send failed: status={} body={}", response.statusCode(), response.body());
                throw new IllegalStateException("Brevo email request failed with status " + response.statusCode());
            }
            LOGGER.info("Brevo email sent successfully: status={}", response.statusCode());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.error("Brevo email request failed", e);
            throw new IllegalStateException("Failed to send email via Brevo", e);
        }
    }
}
