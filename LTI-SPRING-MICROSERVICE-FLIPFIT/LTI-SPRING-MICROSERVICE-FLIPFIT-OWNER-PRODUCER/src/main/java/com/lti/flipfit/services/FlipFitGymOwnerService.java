package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymSlot;

import java.util.List;

/**
 * Author :
 * Version : 1.0
 * Description : Service for Gym Owners to manage centers and bookings.
 */
public interface FlipFitGymOwnerService {

    boolean approveBooking(Long bookingId);

    GymCenter addCenter(GymCenter center, Long ownerId);

    GymCenter updateCenter(GymCenter center);

    List<GymBooking> viewAllBookings(Long centerId);

    List<GymCenter> getCentersByOwner(Long ownerId);

    void addSlot(GymSlot slot, Long centerId);

}
