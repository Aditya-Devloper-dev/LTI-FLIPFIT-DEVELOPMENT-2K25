package com.lti.flipfit.services;

import org.springframework.stereotype.Service;

@Service
public class FlipFitGymNotificationServiceImpl implements FlipFitGymNotificationService {

    @Override
    public boolean sendNotification(String receiverId, String message, String type) {

        System.out.println("Dummy Notification Sent:");
        System.out.println("To: " + receiverId);
        System.out.println("Message: " + message);
        System.out.println("Type: " + type);

        return true; // simulate success
    }
}
