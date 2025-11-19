package com.lti.flipfit.services;

import com.lti.flipfit.beans.GymCenter;
import com.lti.flipfit.beans.Slot;

import java.time.LocalDate;
import java.util.List;

public interface FlipFitGymCenterService {

    List<Slot> getSlotsByDate(String centerId, LocalDate date);

    boolean updateCenterInfo(String centerId, GymCenter updatedCenter);
}
