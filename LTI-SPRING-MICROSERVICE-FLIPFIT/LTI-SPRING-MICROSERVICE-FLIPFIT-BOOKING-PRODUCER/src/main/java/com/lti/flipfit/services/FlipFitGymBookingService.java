package com.lti.flipfit.services;

/**
 * Author : 
 * Version : 1.0
 * Description : Service interface for handling Gym Booking operations.
 */

import com.lti.flipfit.entity.GymBooking;

public interface FlipFitGymBookingService {

    /**
     * @methodname - bookSlot
     * @description - Books a new slot after validating duplicate bookings and
     *              booking limits.
     * @param - booking The booking request payload.
     * @return - A success message with the booking ID.
     */
    String bookSlot(GymBooking booking);

    /**
     * @methodname - cancelBooking
     * @description - Cancels an existing booking after validating booking existence
     *              and state.
     * @param - bookingId The unique booking identifier.
     * @return - A success message with the cancelled booking ID.
     */
    String cancelBooking(Long bookingId);

    /**
     * @methodname - viewPayments
     * @description - Retrieves payments based on filter type.
     * @param - filterType The type of filter (ALL, MONTHLY, WEEKLY, DAILY).
     * @param - date The specific date for DAILY filter (YYYY-MM-DD).
     * @return - List of GymPayment objects.
     */
    java.util.List<com.lti.flipfit.entity.GymPayment> viewPayments(String filterType, String date);
}
