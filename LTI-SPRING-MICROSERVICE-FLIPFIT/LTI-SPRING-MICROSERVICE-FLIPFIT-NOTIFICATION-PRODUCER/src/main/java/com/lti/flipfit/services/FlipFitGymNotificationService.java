package com.lti.flipfit.services;

/**
 * Author :
 * Version : 1.0
 * Description : Service to send notifications to users.
 */
public interface FlipFitGymNotificationService {

    /**
     * @methodname - sendNotification
     * @description - Sends a notification to a user.
     * @param - receiverId The ID of the receiver.
     * @param - message The notification message.
     * @param - type The type of notification.
     * @return - True if notification is sent successfully, false otherwise.
     */
    boolean sendNotification(String receiverId, String message, String type);

}
