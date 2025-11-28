package com.lti.flipfit.dao;

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCenter;
import java.util.List;

/**
 * Author :
 * Version : 1.0
 * Description : Interface for Owner Data Access Object.
 * Handles custom database operations for Owner and related entities using JPQL.
 */
public interface FlipFitGymOwnerDAO {

    /**
     * Retrieves all bookings for a specific center.
     * 
     * @param centerId The ID of the center.
     * @return List of GymBooking objects.
     */
    List<GymBooking> findBookingsByCenterId(Long centerId);

    /**
     * Retrieves all centers owned by a specific owner.
     * 
     * @param ownerId The ID of the owner.
     * @return List of GymCenter objects.
     */
    List<GymCenter> findCentersByOwnerId(Long ownerId);
}
