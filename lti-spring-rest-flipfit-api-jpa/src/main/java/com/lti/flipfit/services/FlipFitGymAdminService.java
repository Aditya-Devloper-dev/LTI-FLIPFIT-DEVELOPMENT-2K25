package com.lti.flipfit.services;


import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymSlot;
import org.aspectj.weaver.patterns.ConcreteCflowPointcut;

import java.util.List;

/**
 * Author      :
 * Version     : 1.0
 * Description : Service operations for system admins.
 */
public interface FlipFitGymAdminService {

    String createCenter(GymCenter center);

    String createSlot(String centerId, GymSlot gymSlot);

    List<GymCenter> getAllCenters();

    GymCenter getCenterById(String centerId);
}

