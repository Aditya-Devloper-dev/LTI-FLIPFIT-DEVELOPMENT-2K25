package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.exceptions.center.CenterAlreadyExistsException;
import com.lti.flipfit.exceptions.center.CenterNotFoundException;
import com.lti.flipfit.exceptions.slots.CapacityInvalidException;
import com.lti.flipfit.exceptions.slots.InvalidSlotTimeException;
import com.lti.flipfit.exceptions.slots.SlotAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

@Service
public class FlipFitGymAdminServiceImpl implements FlipFitGymAdminService {

    private final Map<String, GymCenter> centerStore = new HashMap<>();
    private final Map<String, List<GymSlot>> slotStore = new HashMap<>();

    @Override
    public String createCenter(GymCenter center) {

        boolean exists = centerStore.values().stream()
                .anyMatch(c -> c.getCenterName().equalsIgnoreCase(center.getCenterName())
                        && c.getCity().equalsIgnoreCase(center.getCity()));

        if (exists) {
            throw new CenterAlreadyExistsException(
                    "Center already exists in city: " + center.getCity()
            );
        }

        String centerId = UUID.randomUUID().toString();
        center.setCenterId(centerId);

        centerStore.put(centerId, center);
        slotStore.put(centerId, new ArrayList<>());

        return "Center created with ID: " + centerId;
    }

    @Override
    public String createSlot(String centerId, GymSlot slot) {

        GymCenter center = centerStore.get(centerId);
        if (center == null) {
            throw new CenterNotFoundException("Center " + centerId + " not found");
        }

        if (!slot.getEndTime().isAfter(slot.getStartTime())) {
            throw new InvalidSlotTimeException("End time must be after start time");
        }

        if (slot.getCapacity() <= 0) {
            throw new CapacityInvalidException("Capacity must be greater than 0");
        }

        List<GymSlot> existingSlots = slotStore.get(centerId);

        boolean overlap = existingSlots.stream().anyMatch(existing ->
                timesOverlap(
                        existing.getStartTime(),
                        existing.getEndTime(),
                        slot.getStartTime(),
                        slot.getEndTime()
                )
        );

        if (overlap) {
            throw new SlotAlreadyExistsException("A slot already exists in this time range");
        }

        String slotId = UUID.randomUUID().toString();
        slot.setSlotId(slotId);
        slot.setCenter(center);

        slot.setAvailableSeats(slot.getCapacity());
        slot.setStatus("AVAILABLE");

        existingSlots.add(slot);

        return "Slot created for center " + centerId + " with slot ID: " + slotId;
    }

    @Override
    public List<GymCenter> getAllCenters() {
        return new ArrayList<>(centerStore.values());
    }

    @Override
    public GymCenter getCenterById(String centerId) {
        GymCenter center = centerStore.get(centerId);

        if (center == null) {
            throw new CenterNotFoundException("Center " + centerId + " not found");
        }

        return center;
    }

    private boolean timesOverlap(LocalTime s1, LocalTime e1, LocalTime s2, LocalTime e2) {
        return s1.isBefore(e2) && s2.isBefore(e1);
    }
}
