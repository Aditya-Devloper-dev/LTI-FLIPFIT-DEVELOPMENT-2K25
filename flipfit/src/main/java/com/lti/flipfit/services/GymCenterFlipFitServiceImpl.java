package com.lti.flipfit.services;

import org.springframework.stereotype.Service;

/**
 * Author      : Aditya Anand Mishra
 * Version     : 1.0
 * Description : Service implementation for gym center operations.
 */
@Service
public class GymCenterFlipFitServiceImpl implements GymCenterFlipFitService {

    @Override
    public Object getSlotsByDate(String centerId, String date) {
        return null;
    }

    @Override
    public boolean updateCenterInfo(String centerId) {
        return false;
    }
}
