package com.lti.flipfit.services;

/**
 * Author : 
 * Version : 1.0
 * Description : Service interface for managing Gym Centers.
 */

import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymBooking;

import java.util.List;

public interface FlipFitGymCenterService {

    /**
     * @methodname - getSlotsByCenterId
     * @description - Fetches all slots for a given center.
     * @param - centerId The ID of the gym center.
     * @return - A list of GymSlot entities.
     */
    List<GymSlot> getSlotsByCenterId(Long centerId);

    /**
     * @methodname - getCenterById
     * @description - Fetches a gym center by its ID.
     * @param - centerId The ID of the gym center.
     * @return - The GymCenter entity.
     */
    GymCenter getCenterById(Long centerId);

    /**
     * @methodname - getCentersByOwner
     * @description - Retrieves all centers owned by a specific owner.
     * @param - ownerId The ID of the owner.
     * @return - A list of GymCenter objects.
     */
    List<GymCenter> getCentersByOwner(Long ownerId);

    /**
     * @methodname - viewAllBookings
     * @description - Retrieves all bookings for a specific center.
     * @param - centerId The ID of the center.
     * @return - A list of GymBooking objects.
     */
    List<GymBooking> viewAllBookings(Long centerId);

    /**
     * @methodname - updateCenter
     * @description - Updates an existing center's details.
     * @param - center The GymCenter object with updated details.
     * @param - ownerId The ID of the owner.
     * @return - The updated GymCenter object.
     */
    GymCenter updateCenter(GymCenter center, Long ownerId);

}
