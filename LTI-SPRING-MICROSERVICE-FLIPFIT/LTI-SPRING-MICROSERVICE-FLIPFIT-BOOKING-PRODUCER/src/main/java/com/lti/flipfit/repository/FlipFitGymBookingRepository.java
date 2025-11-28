package com.lti.flipfit.repository;

/**
 * Author :
 * Version : 1.0
 * Description : JPA repository for Gym Booking operations.
 */

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.entity.GymSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlipFitGymBookingRepository extends JpaRepository<GymBooking, Long> {

    /**
     * Finds all bookings for a specific customer.
     *
     * @param customer The customer entity.
     * @return A list of bookings for the customer.
     */
    List<GymBooking> findByCustomer(GymCustomer customer);

    /**
     * Finds all bookings for a specific center.
     *
     * @param centerId The ID of the center.
     * @return A list of bookings for the center.
     */
    List<GymBooking> findByCenterCenterId(Long centerId);
}
