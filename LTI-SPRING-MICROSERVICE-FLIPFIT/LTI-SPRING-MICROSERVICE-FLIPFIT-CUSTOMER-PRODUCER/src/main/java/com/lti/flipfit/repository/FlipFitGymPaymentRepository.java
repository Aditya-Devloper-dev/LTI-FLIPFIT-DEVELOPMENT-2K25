package com.lti.flipfit.repository;

/**
 * Author :
 * Version : 1.0
 * Description : JPA repository for Gym Payment operations.
 */

import com.lti.flipfit.entity.GymPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlipFitGymPaymentRepository extends JpaRepository<GymPayment, String> {
    java.util.Optional<GymPayment> findByBookingId(Long bookingId);
}
