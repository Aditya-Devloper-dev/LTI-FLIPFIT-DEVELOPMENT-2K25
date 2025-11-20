package com.lti.flipfit.services;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FlipFitGymOwnerServiceImpl implements FlipFitGymOwnerService {

    @Override
    public boolean approveBooking(String bookingId) {
        // Always success for testing
        return true;
    }

    @Override
    public boolean addCenter(String ownerId, String centerId) {
        // Always success for dummy use case
        return true;
    }

    @Override
    public boolean updateCenter(String centerId) {
        // Always return true for now
        return true;
    }

    @Override
    public List<Map<String, Object>> viewAllBookings(String centerId) {

        // Dummy booking 1
        Map<String, Object> b1 = new HashMap<>();
        b1.put("bookingId", "BK-101");
        b1.put("customerId", "CUS-1");
        b1.put("slotId", "SLOT-1");
        b1.put("status", "BOOKED");

        // Dummy booking 2
        Map<String, Object> b2 = new HashMap<>();
        b2.put("bookingId", "BK-102");
        b2.put("customerId", "CUS-2");
        b2.put("slotId", "SLOT-2");
        b2.put("status", "WAITING");

        return Arrays.asList(b1, b2);
    }
}
