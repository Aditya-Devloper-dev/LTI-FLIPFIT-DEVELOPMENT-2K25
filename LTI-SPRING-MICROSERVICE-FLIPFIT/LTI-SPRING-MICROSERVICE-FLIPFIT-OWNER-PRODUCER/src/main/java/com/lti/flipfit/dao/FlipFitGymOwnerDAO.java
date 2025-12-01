package com.lti.flipfit.dao;

/**
 * Author :
 * Version : 1.0
 * Description : Interface for Owner Data Access Object.
 * Handles custom database operations for Owner and related entities using JPQL.
 */
public interface FlipFitGymOwnerDAO {

    /**
     * Toggles the active status of a center directly in the database.
     * 
     * @param centerId The ID of the center.
     * @param ownerId  The ID of the owner.
     * @return true if the status was updated, false otherwise.
     */
    boolean toggleCenterStatus(Long centerId, Long ownerId);

    /**
     * Toggles the active status of a slot directly in the database.
     * 
     * @param slotId  The ID of the slot.
     * @param ownerId The ID of the owner.
     * @return true if the status was updated, false otherwise.
     */
    boolean toggleSlotStatus(Long slotId, Long ownerId);
}
