package com.lti.flipfit.services;

import com.lti.flipfit.beans.Booking;

import java.util.List;

/**
 * Author      :
 * Version     : 1.0
 * Description : Handles booking creation, updates, and cancellations.
 */

public interface FlipFitGymBookingService {

    String bookSlot(Booking booking);

    String cancelBooking(String bookingId);

    List<Booking> getUserBookings(String userId);
}

