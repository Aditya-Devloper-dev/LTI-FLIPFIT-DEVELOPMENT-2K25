package com.lti.flipfit.services;

import com.lti.flipfit.dto.NotificationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "notification-topic";

    public void sendNotification(NotificationEvent event) {
        kafkaTemplate.send(TOPIC, event);
        System.out.println("Notification sent to Kafka: " + event);
    }
}
