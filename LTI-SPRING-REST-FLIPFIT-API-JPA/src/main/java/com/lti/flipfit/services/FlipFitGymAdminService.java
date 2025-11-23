package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymOwner;
import com.lti.flipfit.entity.GymSlot;
import java.util.List;

/**
 * Author :
 * Version : 1.0
 * Description : Service interface for handling Gym Admin operations.
 */

public interface FlipFitGymAdminService {

    // Removed createSlot methods as they are now in Owner Service

    List<GymCenter> getAllCenters();

    GymCenter getCenterById(Long centerId);

    String approveOwner(Long ownerId);

    List<GymOwner> getPendingOwners();

    String approveCenter(Long centerId);

    List<GymCenter> getPendingCenters();

    String approveSlot(Long slotId);

    List<GymSlot> getPendingSlots(Long centerId);

    void deleteCenter(Long centerId);

    void deleteSlot(Long slotId);
}
