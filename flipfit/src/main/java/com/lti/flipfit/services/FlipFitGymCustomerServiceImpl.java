package com.lti.flipfit.services;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FlipFitGymCustomerServiceImpl implements FlipFitGymCustomerService {

    @Override
    public List<Map<String, Object>> viewAvailability(String centerId, String date) {

        Map<String, Object> s1 = new HashMap<>();
        s1.put("slotId", "SLOT-1");
        s1.put("startTime", "06:00");
        s1.put("endTime", "07:00");
        s1.put("availableSeats", 5);

        Map<String, Object> s2 = new HashMap<>();
        s2.put("slotId", "SLOT-2");
        s2.put("startTime", "07:00");
        s2.put("endTime", "08:00");
        s2.put("availableSeats", 0);

        return Arrays.asList(s1, s2);
    }

    @Override
    public String bookSlot(String customerId, String slotId, String centerId) {
        return "Booking successful for customer " + customerId +
                " in slot " + slotId +
                " at center " + centerId;
    }

    @Override
    public boolean cancelBooking(String bookingId) {
        return true; // always succeed for dummy implementation
    }
}
