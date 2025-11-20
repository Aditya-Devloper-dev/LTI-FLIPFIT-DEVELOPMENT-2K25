package com.lti.flipfit.services;

import java.util.List;
import java.util.Map;

/**
 * Author      :
 * Version     : 1.0
 * Description : Service for Gym Owners to manage centers and bookings.
 */
public interface FlipFitGymOwnerService {

    boolean approveBooking(String bookingId);

    boolean addCenter(String ownerId, String centerId);

    boolean updateCenter(String centerId);

    List<Map<String, Object>> viewAllBookings(String centerId);

}
