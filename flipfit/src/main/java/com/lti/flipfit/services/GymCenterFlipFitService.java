package com.lti.flipfit.services;

import java.util.List;
import java.util.Map;

/**
 * Author      :
 * Version     : 1.0
 * Description : Operations related to gym centers.
 */
public interface GymCenterFlipFitService {

    List<Map<String, Object>> getSlotsByDate(String centerId, String date);

    boolean updateCenterInfo(String centerId);

}
