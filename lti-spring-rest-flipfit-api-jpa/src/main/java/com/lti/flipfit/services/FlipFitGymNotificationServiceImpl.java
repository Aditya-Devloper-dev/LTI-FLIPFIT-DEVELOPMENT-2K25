package com.lti.flipfit.services;

/**
 * Author :
 * Version : 1.0
 * Description : Implementation of the FlipFitGymNotificationService interface.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FlipFitGymNotificationServiceImpl implements FlipFitGymNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymNotificationServiceImpl.class);

    @Override
    public boolean sendNotification(String receiverId, String message, String type) {
        logger.info("Sending notification to: {}, Type: {}, Message: {}", receiverId, type, message);

        System.out.println("Dummy Notification Sent:");
        System.out.println("To: " + receiverId);
        System.out.println("Message: " + message);
        System.out.println("Type: " + type);

        return true; // simulate success
    }
}
