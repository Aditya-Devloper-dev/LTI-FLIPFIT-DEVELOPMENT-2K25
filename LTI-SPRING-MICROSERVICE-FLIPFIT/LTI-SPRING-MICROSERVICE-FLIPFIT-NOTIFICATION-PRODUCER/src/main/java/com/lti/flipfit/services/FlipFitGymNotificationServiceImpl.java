package com.lti.flipfit.services;

/**
 * Author : 
 * Version : 1.0
 * Description : Implementation of the FlipFitGymNotificationService interface.
 */

import com.lti.flipfit.entity.GymNotification;
import com.lti.flipfit.entity.User;
import com.lti.flipfit.repository.FlipFitGymNotificationRepository;
import com.lti.flipfit.repository.FlipFitGymUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FlipFitGymNotificationServiceImpl implements FlipFitGymNotificationService {

    private final FlipFitGymNotificationRepository notificationRepo;
    private final FlipFitGymUserRepository userRepo;
    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymNotificationServiceImpl.class);

    @Autowired
    public FlipFitGymNotificationServiceImpl(FlipFitGymNotificationRepository notificationRepo,
            FlipFitGymUserRepository userRepo) {
        this.notificationRepo = notificationRepo;
        this.userRepo = userRepo;
    }

    /**
     * @methodname - sendNotification
     * @description - Sends a notification to a user.
     * @param - receiverId The ID of the receiver.
     * @param - message The notification message.
     * @param - type The type of notification.
     * @return - True if notification is sent successfully, false otherwise.
     */
    @Override
    public boolean sendNotification(String receiverId, String message, String type) {
        logger.info("Sending notification to: {}, Type: {}, Message: {}", receiverId, type, message);

        try {
            Long userId = Long.parseLong(receiverId);
            User receiver = userRepo.findById(userId).orElse(null);

            if (receiver != null) {
                GymNotification notification = new GymNotification();
                notification.setNotificationId(UUID.randomUUID().toString());
                notification.setReceiver(receiver);
                notification.setMessage(message);
                notification.setType(type);
                notification.setStatus("UNREAD");
                notification.setCreatedAt(LocalDateTime.now());

                notificationRepo.save(notification);
                logger.info("Notification saved to database.");
                return true;
            } else {
                logger.error("User not found with ID: {}", receiverId);
                return false;
            }
        } catch (Exception e) {
            logger.error("Error saving notification", e);
            return false;
        }
    }

    /**
     * @methodname - getNotifications
     * @description - Retrieves notifications for a user.
     * @param - userId The ID of the user.
     * @return - List of notifications.
     */
    @Override
    public List<GymNotification> getNotifications(Long userId) {
        return notificationRepo.findByReceiver_UserIdOrderByCreatedAtDesc(userId);
    }
}
