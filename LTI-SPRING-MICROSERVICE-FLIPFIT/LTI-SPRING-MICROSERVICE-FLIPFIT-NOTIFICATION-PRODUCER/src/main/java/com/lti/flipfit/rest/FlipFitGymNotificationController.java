package com.lti.flipfit.rest;

import com.lti.flipfit.entity.GymNotification;
import com.lti.flipfit.services.FlipFitGymNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Author :
 * Version : 1.0
 * Description : Controller for sending notifications.
 */
@RestController
@RequestMapping("/notification")
public class FlipFitGymNotificationController {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymNotificationController.class);

    private final FlipFitGymNotificationService service;

    public FlipFitGymNotificationController(FlipFitGymNotificationService service) {
        this.service = service;
    }

    /**
     * @methodname - sendNotification
     * @description - Sends a notification to a user.
     * @param - receiverId The ID of the receiver.
     * @param - message The notification message.
     * @param - type The type of notification.
     * @return - True if notification is sent successfully, false otherwise.
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public boolean sendNotification(@RequestParam String receiverId,
            @RequestParam String message,
            @RequestParam String type) {
        logger.info("Received request to send notification to: {}, Type: {}", receiverId, type);

        return service.sendNotification(receiverId, message, type);
    }

    /**
     * @methodname - getNotifications
     * @description - Retrieves notifications for a user.
     * @param - userId The ID of the user.
     * @return - List of notifications.
     */
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public List<GymNotification> getNotifications(@PathVariable Long userId) {
        logger.info("Received request to get notifications for user ID: {}", userId);
        return service.getNotifications(userId);
    }

    /**
     * @methodname - clearAllNotifications
     * @description - Deletes all notifications for a user.
     * @param - userId The ID of the user.
     */
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
    public void clearAllNotifications(@PathVariable Long userId) {
        logger.info("Received request to clear all notifications for user ID: {}", userId);
        service.clearAllNotifications(userId);
    }
}
