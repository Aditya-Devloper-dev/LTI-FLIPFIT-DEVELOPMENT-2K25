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

    /**
     * @methodname - toggleCenterActive
     * @description - Toggles the active status of a center.
     * @param - centerId The ID of the center.
     * @param - ownerId The ID of the owner.
     * @return - The new active status (true/false).
     */
    boolean toggleCenterActive(Long centerId, Long ownerId);

    /**
     * @methodname - toggleSlotActive
     * @description - Toggles the active status of a slot.
     * @param - slotId The ID of the slot.
     * @param - ownerId The ID of the owner.
     * @return - The new active status (true/false).
     */
    boolean toggleSlotActive(Long slotId, Long ownerId);

    /**
     * @methodname - addCenter
     * @description - Adds a new center for a specific owner.
     * @param - center The GymCenter object to add.
     * @param - ownerId The ID of the owner.
     * @return - The added GymCenter object.
     */
    GymCenter addCenter(GymCenter center, Long ownerId);

    /**
     * @methodname - updateCenter
     * @description - Updates an existing center's details.
     * @param - center The GymCenter object with updated details.
     * @param - ownerId The ID of the owner.
     * @return - The updated GymCenter object.
     */
    GymCenter updateCenter(GymCenter center, Long ownerId);

    /**
     * @methodname - viewAllBookings
     * @description - Retrieves all bookings for a specific center.
     * @param - centerId The ID of the center.
     * @return - A list of GymBooking objects.
     */
    List<GymBooking> viewAllBookings(Long centerId);

    /**
     * @methodname - getCentersByOwner
     * @description - Retrieves all centers owned by a specific owner.
     * @param - ownerId The ID of the owner.
     * @return - A list of GymCenter objects.
     */
    List<GymCenter> getCentersByOwner(Long ownerId);

    /**
     * @methodname - addSlot
     * @description - Adds a new slot to a center.
     * @param - slot The GymSlot object to add.
     * @param - centerId The ID of the center.
     * @param - ownerId The ID of the owner.
     */
    void addSlot(GymSlot slot, Long centerId, Long ownerId);

}
