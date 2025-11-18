package com.lti.flipfit.services;

/**
 * Author      :
 * Version     : 1.0
 * Description : Service to send notifications to users.
 */
public interface FlipFitGymNotificationService {

    boolean sendNotification(String receiverId, String message, String type);

}
