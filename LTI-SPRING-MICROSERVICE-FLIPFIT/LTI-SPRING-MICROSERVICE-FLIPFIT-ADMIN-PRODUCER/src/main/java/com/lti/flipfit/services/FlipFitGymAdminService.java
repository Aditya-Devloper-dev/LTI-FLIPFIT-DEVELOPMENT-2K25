package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymOwner;
import com.lti.flipfit.entity.GymPayment;
import com.lti.flipfit.entity.GymSlot;
import java.util.List;

/**
 * Author :
 * Version : 1.0
 * Description : Service interface for handling Gym Admin operations.
 */

public interface FlipFitGymAdminService {

    /**
     * @methodname - getAllCenters
     * @description - Retrieves all registered gym centers.
     * @return - List of all GymCenter objects.
     */
    List<GymCenter> getAllCenters();

    /**
     * @methodname - getCenterById
     * @description - Retrieves a specific center by its ID.
     * @param - centerId The ID of the center.
     * @return - The GymCenter object.
     */
    GymCenter getCenterById(Long centerId);

    /**
     * @methodname - approveOwner
     * @description - Approves a pending gym owner.
     * @param - ownerId The ID of the owner to approve.
     * @return - A success message or error message.
     */
    String approveOwner(Long ownerId);

    /**
     * @methodname - getPendingOwners
     * @description - Retrieves all pending gym owners.
     * @return - List of pending GymOwner objects.
     */
    List<GymOwner> getPendingOwners();

    /**
     * @methodname - approveCenter
     * @description - Approves a pending gym center.
     * @param - centerId The ID of the center to approve.
     * @return - A success message.
     */
    String approveCenter(Long centerId);

    /**
     * @methodname - getPendingCenters
     * @description - Retrieves all pending gym centers.
     * @return - List of pending GymCenter objects.
     */
    List<GymCenter> getPendingCenters();

    /**
     * @methodname - approveSlot
     * @description - Approves a pending slot.
     * @param - slotId The ID of the slot to approve.
     * @return - A success message or error message.
     */
    String approveSlot(Long slotId);

    /**
     * @methodname - getPendingSlots
     * @description - Retrieves all pending slots for a specific center.
     * @param - centerId The ID of the center.
     * @return - List of pending GymSlot objects.
     */
    List<GymSlot> getPendingSlots(Long centerId);

    /**
     * @methodname - deleteCenter
     * @description - Deletes a gym center by its ID.
     * @param - centerId The ID of the center to delete.
     */
    void deleteCenter(Long centerId);

    /**
     * @methodname - deleteSlot
     * @description - Deletes a gym slot by its ID.
     * @param - slotId The ID of the slot to delete.
     */
    void deleteSlot(Long slotId);

    /**
     * @methodname - viewPayments
     * @description - Retrieves payments based on filter type.
     * @param - filterType The type of filter (ALL, MONTHLY, WEEKLY, DAILY).
     * @param - date The specific date for DAILY filter (YYYY-MM-DD).
     * @return - List of GymPayment objects.
     */
    List<GymPayment> viewPayments(String filterType, String date);
}
