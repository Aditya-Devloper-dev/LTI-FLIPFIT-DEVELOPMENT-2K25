package com.lti.flipfit.utils;

/**
 * Author :
 * Version : 1.0
 * Description : Helper class for sending user notifications. Handles welcome notification logic.
 */

import com.lti.flipfit.constants.RoleType;
import com.lti.flipfit.dto.NotificationEvent;
import com.lti.flipfit.entity.User;
import com.lti.flipfit.services.NotificationProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserNotificationHelper {

    private static final Logger logger = LoggerFactory.getLogger(UserNotificationHelper.class);

    @Autowired
    private NotificationProducer notificationProducer;

    /**
     * @methodname - sendWelcomeNotification
     * @description - Sends a welcome notification to the user upon successful
     *              registration.
     * @param - user The user to whom the notification is sent.
     */
    public void sendWelcomeNotification(User user) {
        try {
            String subject = "Welcome to FlipFit!";
            String message = "Welcome to FlipFit! We are glad to have you.";

            if (RoleType.CUSTOMER.name().equals(user.getRole().getRoleId())) {
                message = "Welcome to our platform! Start your fitness journey with us today.";
            } else if (RoleType.OWNER.name().equals(user.getRole().getRoleId())) {
                message = "Welcome to our platform! Admin is looking on your profile. Once you got approval we will notify you and you start your business.";
            }

            NotificationEvent event = new NotificationEvent(user.getEmail(), subject, message);
            notificationProducer.sendNotification(event);
        } catch (Exception e) {
            logger.error("Failed to send welcome notification", e);
        }
    }
}
