package com.lti.flipfit.services;

/**
 * Author : 
 * Version : 1.0
 * Description : Service interface for managing Gym Centers.
 */

import com.lti.flipfit.entity.GymSlot;

import java.util.List;

public interface FlipFitGymCenterService {

    /**
     * @methodname - getSlotsByDate
     * @description - Fetches all slots for a given center on a specific date.
     * @param - centerId The ID of the gym center.
     * @param - date The date to check availability for.
     * @return - A list of GymSlot entities.
     */
    List<GymSlot> getSlotsByDate(Long centerId, String date);

    /**
     * @methodname - getSlotsByCenterId
     * @description - Fetches all slots for a given center.
     * @param - centerId The ID of the gym center.
     * @return - A list of GymSlot entities.
     */
    List<GymSlot> getSlotsByCenterId(Long centerId);

}
