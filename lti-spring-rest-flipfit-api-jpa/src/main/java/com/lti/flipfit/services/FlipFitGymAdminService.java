package com.lti.flipfit.services;

/**
 * Author :
 * Version : 1.0
 * Description : Service interface for handling Gym Admin operations.
 */

import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymSlot;
import org.aspectj.weaver.patterns.ConcreteCflowPointcut;

import java.util.List;
public interface FlipFitGymAdminService {

    String createCenter(GymCenter center);

    String createSlot(Long centerId, GymSlot gymSlot);

    List<GymCenter> getAllCenters();

    GymCenter getCenterById(Long centerId);
}
