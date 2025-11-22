package com.lti.flipfit.repository;

/**
 * Author :
 * Version : 1.0
 * Description : JPA repository for Gym Notification operations.
 */

import com.lti.flipfit.entity.GymNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlipFitGymNotificationRepository extends JpaRepository<GymNotification, String> {
}
