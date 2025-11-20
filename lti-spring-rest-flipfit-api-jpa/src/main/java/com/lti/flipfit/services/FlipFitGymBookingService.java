package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymBooking;

import java.util.List;

/**
 * Author      :
 * Version     : 1.0
 * Description : Handles booking creation, updates, and cancellations.
 */

public interface FlipFitGymBookingService {

    String bookSlot(GymBooking booking);

    String cancelBooking(String bookingId);

    List<GymBooking> getUserBookings(String userId);
}

