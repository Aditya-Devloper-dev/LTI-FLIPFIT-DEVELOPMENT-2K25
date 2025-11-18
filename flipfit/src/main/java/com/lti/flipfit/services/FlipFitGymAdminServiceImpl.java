package com.lti.flipfit.services;

import com.lti.flipfit.beans.GymCenter;
import com.lti.flipfit.beans.Slot;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FlipFitGymAdminServiceImpl implements FlipFitGymAdminService {

    // Temporary in-memory store for dummy testing
    private final Map<String, GymCenter> centerStore = new HashMap<>();

    @Override
    public String createCenter(GymCenter center) {
        String id = UUID.randomUUID().toString();
        center.setCenterId(id);
        centerStore.put(id, center);
        return "Center created with ID: " + id;
    }

    @Override
    public String createSlot(String centerId, Slot slot) {
        // Dummy: just return success
        return "Slot created for centerId: " + centerId;
    }

    @Override
    public List<GymCenter> getAllCenters() {
        return new ArrayList<>(centerStore.values());
    }

    @Override
    public GymCenter getCenterById(String centerId) {
        return centerStore.get(centerId);
    }
}
