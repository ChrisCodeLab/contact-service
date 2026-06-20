package com.chriscodelab.contact.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.chriscodelab.contact.dto.request.ContactRequestDTO;
import com.chriscodelab.contact.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;
    private final String fromEmail;
    private final String ownerEmail;

    public EmailServiceImpl(JavaMailSender mailSender,
            @Value("${spring.mail.username}") String fromEmail,
            @Value("${contact.owner.email}") String ownerEmail) {
        this.mailSender = mailSender;
        this.fromEmail = fromEmail;
        this.ownerEmail = ownerEmail;
    }

    @Override
    public void sendContactEmails(ContactRequestDTO request) {
        sendConfirmationEmail(request);
        sendOwnerNotificationEmail(request);
    }

    private void sendConfirmationEmail(ContactRequestDTO request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(request.getEmail());
        message.setSubject("Thank you for contacting ChrisCodeLab");
        message.setText("Hello " + request.getName() + ",\n\n"
                + "Thank you for reaching out to me. I will get back to you as soon as I can.\n\n"
                + "Best regards,\n"
                + "ChrisCodeLab");

        mailSender.send(message);
    }

    private void sendOwnerNotificationEmail(ContactRequestDTO request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(ownerEmail);
        message.setSubject("chriscodelab: New contact message from " + request.getName());
        message.setText("You have received a message from " + request.getName() + " <" + request.getEmail() + ">.\n\n"
                + "Message:\n" + request.getMessage() + "\n\n\n"
                + "This is an automated notification from your contact-service.");

        mailSender.send(message);
    }

}
