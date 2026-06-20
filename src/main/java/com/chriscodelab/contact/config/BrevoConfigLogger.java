package com.chriscodelab.contact.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class BrevoConfigLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrevoConfigLogger.class);

    private final String apiKey;
    private final String apiUrl;
    private final String senderEmail;
    private final String senderName;
    private final String ownerEmail;
    private final boolean debug;

    public BrevoConfigLogger(
            @Value("${brevo.api.key:}") String apiKey,
            @Value("${brevo.api.url:}") String apiUrl,
            @Value("${brevo.api.sender-email:}") String senderEmail,
            @Value("${brevo.api.sender-name:}") String senderName,
            @Value("${brevo.owner-email:}") String ownerEmail,
            @Value("${brevo.debug:false}") boolean debug) {
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.senderEmail = senderEmail;
        this.senderName = senderName;
        this.ownerEmail = ownerEmail;
        this.debug = debug;
    }

    @PostConstruct
    public void logConfig() {
        if (apiKey == null || apiKey.isBlank()) {
            LOGGER.warn("Brevo API key is NOT set (BREVO_API_KEY).");
        } else {
            LOGGER.info("Brevo API key present: {}", maskApiKey(apiKey));
            if (apiKey.startsWith("xsmt")) {
                LOGGER.warn("Brevo API key appears to be an SMTP key (starts with 'xsmt'). The HTTP REST API may require a different API key. If you see 401 responses, use a Brevo REST API key instead of an SMTP key.");
            }
        }

        LOGGER.info("Brevo API URL: {}", apiUrl == null || apiUrl.isBlank() ? "(not set)" : apiUrl);
        LOGGER.info("Brevo sender: {} <{}>", senderName == null || senderName.isBlank() ? "(not set)" : senderName,
            senderEmail == null || senderEmail.isBlank() ? "(not set)" : senderEmail);
        if (ownerEmail == null || ownerEmail.isBlank()) {
            LOGGER.info("Brevo owner recipient: (not configured - using submitter's email for owner notifications)");
        } else {
            LOGGER.info("Brevo owner recipient: {}", ownerEmail);
        }
        LOGGER.info("Brevo debug mode: {}", debug);
    }

    private String maskApiKey(String key) {
        int len = key.length();
        if (len <= 8) return "****";
        String first = key.substring(0, 4);
        String last = key.substring(len - 4);
        return first + "..." + last + " (len=" + len + ")";
    }
}
