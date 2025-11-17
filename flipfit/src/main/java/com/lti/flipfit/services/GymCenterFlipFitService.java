package com.lti.flipfit.services;

/**
 * Author      :
 * Version     : 1.0
 * Description : Operations related to gym centers.
 */
public interface GymCenterFlipFitService {

    Object getSlotsByDate(String centerId, String date);

    boolean updateCenterInfo(String centerId);

}
