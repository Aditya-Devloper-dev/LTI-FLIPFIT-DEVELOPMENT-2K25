package com.lti.flipfit.services;

import com.lti.flipfit.dto.NotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

    private static final Logger logger = LoggerFactory.getLogger(NotificationProducer.class);
    private static final String TOPIC = "notification-topic";

    @Autowired
    private KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    public void sendNotification(NotificationEvent event) {
        logger.info("Sending notification to topic: {}", TOPIC);
        kafkaTemplate.send(TOPIC, event);
    }
}
