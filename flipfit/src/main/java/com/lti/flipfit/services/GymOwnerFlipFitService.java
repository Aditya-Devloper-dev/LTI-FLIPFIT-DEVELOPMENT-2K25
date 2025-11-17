package com.lti.flipfit.services;

/**
 * Author      :
 * Version     : 1.0
 * Description : Service for Gym Owners to manage centers and bookings.
 */
public interface GymOwnerFlipFitService {

    boolean approveBooking(String bookingId);

    boolean addCenter(String ownerId, String centerId);

    boolean updateCenter(String centerId);

    Object viewAllBookings(String centerId);

}
