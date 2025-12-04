package com.lti.flipfit.services;

/**
 * Author : 
 * Version : 1.0
 * Description : Service interface for managing Gym Centers.
 */

import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.entity.GymCenter;

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

}
