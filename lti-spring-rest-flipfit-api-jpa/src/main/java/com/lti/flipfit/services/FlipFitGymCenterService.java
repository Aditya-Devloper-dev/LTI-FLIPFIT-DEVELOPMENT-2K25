package com.lti.flipfit.services;

/**
 * Author :
 * Version : 1.0
 * Description : Service interface for managing Gym Centers.
 */

import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymSlot;

import java.time.LocalDate;
import java.util.List;

public interface FlipFitGymCenterService {

    List<GymSlot> getSlotsByDate(Long centerId, LocalDate date);

    boolean updateCenterInfo(Long centerId, GymCenter updatedCenter);
}
