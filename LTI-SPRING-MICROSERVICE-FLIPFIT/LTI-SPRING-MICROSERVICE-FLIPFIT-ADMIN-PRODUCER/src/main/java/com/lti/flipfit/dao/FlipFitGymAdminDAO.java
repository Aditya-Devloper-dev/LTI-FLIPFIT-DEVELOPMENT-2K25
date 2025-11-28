package com.lti.flipfit.dao;

import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymOwner;
import com.lti.flipfit.entity.GymSlot;
import java.util.List;

/**
 * Author :
 * Version : 1.0
 * Description : Interface for Admin Data Access Object.
 * Handles custom database operations for Admin using JPQL.
 */
public interface FlipFitGymAdminDAO {

    /**
     * Retrieves all pending gym owners.
     * 
     * @return List of pending GymOwner objects.
     */
    List<GymOwner> findPendingOwners();

    /**
     * Retrieves all pending gym centers.
     * 
     * @return List of pending GymCenter objects.
     */
    List<GymCenter> findPendingCenters();

    /**
     * Retrieves all pending slots for a specific center.
     * 
     * @param centerId The ID of the center.
     * @return List of pending GymSlot objects.
     */
    List<GymSlot> findPendingSlots(Long centerId);

    /**
     * Approves a gym owner by ID.
     * 
     * @param ownerId The ID of the owner.
     * @return Number of rows updated.
     */
    int approveOwner(Long ownerId);

    /**
     * Approves a gym center by ID.
     * 
     * @param centerId The ID of the center.
     * @return Number of rows updated.
     */
    int approveCenter(Long centerId);

    /**
     * Approves a gym slot by ID.
     * 
     * @param slotId The ID of the slot.
     * @return Number of rows updated.
     */
    int approveSlot(Long slotId);
}
