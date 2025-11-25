package com.lti.flipfit.repository;

/**
 * Author : Shiny Sunaina
 * Version : 1.0
 * Description : JPA repository for Gym Booking operations.
 */

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.entity.GymSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlipFitGymBookingRepository extends JpaRepository<GymBooking, Long> {

    boolean existsByCustomerAndSlot(GymCustomer customer, GymSlot slot);

    List<GymBooking> findByCustomer(GymCustomer customer);

    List<GymBooking> findByCenterCenterId(Long centerId);
}
