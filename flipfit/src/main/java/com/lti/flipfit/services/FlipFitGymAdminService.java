package com.lti.flipfit.services;

import com.lti.flipfit.beans.GymCenter;
import com.lti.flipfit.beans.Slot;

import java.util.List;

/**
 * Author      :
 * Version     : 1.0
 * Description : Service operations for system admins.
 */
public interface FlipFitGymAdminService {

    String createCenter(GymCenter center);

    String createSlot(String centerId, Slot slot);

    List<GymCenter> getAllCenters();

    GymCenter getCenterById(String centerId);
}

