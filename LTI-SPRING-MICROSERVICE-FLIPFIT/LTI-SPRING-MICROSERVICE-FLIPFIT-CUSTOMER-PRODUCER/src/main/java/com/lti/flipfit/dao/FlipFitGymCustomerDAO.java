package com.lti.flipfit.dao;

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymSlot;
import java.time.LocalDate;
import java.util.List;

/**
 * Author :
 * Version : 1.0
 * Description : Interface for Customer Data Access Object.
 * Handles custom database operations for Customer using JPQL.
 */
public interface FlipFitGymCustomerDAO {

    /**
     * Retrieves all active slots.
     * 
     * @return List of GymSlot entities.
     */
    List<GymSlot> findAllActiveSlots();

    /**
     * Retrieves all bookings made by a customer.
     * 
     * @param customerId The ID of the customer.
     * @return List of GymBooking entities.
     */
    List<GymBooking> findBookingsByCustomerId(Long customerId);

    /**
     * Retrieves all active gym centers.
     * 
     * @return List of active GymCenter entities.
     */
    List<GymCenter> findActiveGyms();
}
