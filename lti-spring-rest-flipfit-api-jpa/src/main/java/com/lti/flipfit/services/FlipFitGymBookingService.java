package com.lti.flipfit.services;

/**
 * Author :
 * Version : 1.0
 * Description : Service interface for handling Gym Booking operations.
 */

import com.lti.flipfit.entity.GymBooking;

import java.util.List;

public interface FlipFitGymBookingService {

    String bookSlot(GymBooking booking);

    String cancelBooking(Long bookingId);

    List<GymBooking> getUserBookings(Long userId);
}
