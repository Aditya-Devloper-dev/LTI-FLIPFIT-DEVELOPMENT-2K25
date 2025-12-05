package com.lti.flipfit.dao;

import com.lti.flipfit.entity.GymPayment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Author :
 * Version : 1.0
 * Description : Interface for Booking Data Access Object.
 * Handles custom database operations for Booking using JPQL.
 */
public interface FlipFitGymBookingDAO {

    /**
     * Checks if a booking exists for a specific customer and slot on a given date.
     * 
     * @param customerId  The ID of the customer.
     * @param slotId      The ID of the slot.
     * @param bookingDate The date of the booking.
     * @return true if booking exists, false otherwise.
     */
    boolean checkDuplicateBooking(Long customerId, Long slotId, LocalDate bookingDate);

    /**
     * Retrieves payments within a specific date range.
     * 
     * @param startDate The start date and time.
     * @param endDate   The end date and time.
     * @return List of GymPayment entities.
     */
    List<GymPayment> findPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}
