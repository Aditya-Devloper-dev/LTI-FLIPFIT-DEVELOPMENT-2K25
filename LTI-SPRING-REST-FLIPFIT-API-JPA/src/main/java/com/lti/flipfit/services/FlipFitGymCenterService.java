package com.lti.flipfit.services;

/**
 * Author :
 * Version : 1.0
 * Description : Service interface for managing Gym Centers.
 */

import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymSlot;

import java.util.List;

public interface FlipFitGymCenterService {

    List<GymSlot> getSlotsByDate(Long centerId, String date);

    List<GymSlot> getSlotsByCenterId(Long centerId);

    boolean updateCenterInfo(Long centerId, GymCenter updatedCenter);
}
