package com.lti.flipfit.repository;

import com.lti.flipfit.entity.GymNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlipFitGymNotificationRepository extends JpaRepository<GymNotification, String> {
    List<GymNotification> findByReceiver_UserIdOrderByCreatedAtDesc(Long userId);

    void deleteByReceiver_UserId(Long userId);
}
