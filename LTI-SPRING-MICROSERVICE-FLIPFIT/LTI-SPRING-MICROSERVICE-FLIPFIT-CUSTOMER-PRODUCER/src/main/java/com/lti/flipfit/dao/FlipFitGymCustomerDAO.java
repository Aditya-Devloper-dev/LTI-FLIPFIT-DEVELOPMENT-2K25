package com.lti.flipfit.dao;

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCenter;
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
     * Retrieves slots and their booked count for a specific center and date.
     * 
     * @param centerId The ID of the center.
     * @param date     The date to check availability.
     * @return List of Object arrays containing GymSlot and booked count (Long).
     */
    List<Object[]> findSlotAvailability(Long centerId, LocalDate date);

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
