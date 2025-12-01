package com.lti.flipfit.service;

import com.lti.flipfit.dto.NotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    private static final Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);

    @Autowired
    private JavaMailSender mailSender;

    @KafkaListener(topics = "notification-topic", groupId = "notification-group")
    public void consume(NotificationEvent event) {
        logger.info("Received notification event: {}", event);

        try {
            sendEmail(event.getRecipientEmail(), event.getSubject(), event.getMessage());
            logger.info("Email sent successfully to: {}", event.getRecipientEmail());
        } catch (Exception e) {
            logger.error("Failed to send email to: {}", event.getRecipientEmail(), e);
        }
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
