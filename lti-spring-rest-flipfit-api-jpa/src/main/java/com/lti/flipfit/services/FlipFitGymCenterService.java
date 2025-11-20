package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymSlot;

import java.time.LocalDate;
import java.util.List;

public interface FlipFitGymCenterService {

    List<GymSlot> getSlotsByDate(String centerId, LocalDate date);

    boolean updateCenterInfo(String centerId, GymCenter updatedCenter);
}
