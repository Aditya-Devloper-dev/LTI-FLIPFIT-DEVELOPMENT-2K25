package com.lti.flipfit.services;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GymCenterFlipFitServiceImpl implements GymCenterFlipFitService {

    @Override
    public List<Map<String, Object>> getSlotsByDate(String centerId, String date) {

        Map<String, Object> slot1 = new HashMap<>();
        slot1.put("slotId", "SLOT-101");
        slot1.put("startTime", "06:00");
        slot1.put("endTime", "07:00");
        slot1.put("capacity", 20);
        slot1.put("availableSeats", 12);

        Map<String, Object> slot2 = new HashMap<>();
        slot2.put("slotId", "SLOT-102");
        slot2.put("startTime", "07:00");
        slot2.put("endTime", "08:00");
        slot2.put("capacity", 20);
        slot2.put("availableSeats", 0);

        return Arrays.asList(slot1, slot2);
    }

    @Override
    public boolean updateCenterInfo(String centerId) {
        // always return true for dummy test
        return true;
    }
}
