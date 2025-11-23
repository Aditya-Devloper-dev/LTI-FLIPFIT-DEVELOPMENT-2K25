package com.lti.flipfit.services;

/**
 * Author :
 * Version : 1.0
 * Description : Service interface for handling Gym Booking operations.
 */

import com.lti.flipfit.entity.GymBooking;

public interface FlipFitGymBookingService {

    String bookSlot(GymBooking booking);

    String cancelBooking(Long bookingId);
}
