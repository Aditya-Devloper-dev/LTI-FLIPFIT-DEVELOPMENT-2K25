package com.lti.flipfit.services;

import com.lti.flipfit.beans.GymCenter;
import com.lti.flipfit.beans.Slot;
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
    private final Map<String, List<Slot>> slotStore = new HashMap<>();

    @Override
    public String createCenter(GymCenter center) {

        // Check if center already exists (name + city combination)
        boolean exists = centerStore.values().stream()
                .anyMatch(c -> c.getCenterName().equalsIgnoreCase(center.getCenterName())
                        && c.getCity().equalsIgnoreCase(center.getCity()));

        if (exists) {
            throw new CenterAlreadyExistsException(
                    "Center already exists in city: " + center.getCity()
            );
        }

        // Generate center ID
        String centerId = UUID.randomUUID().toString();
        center.setCenterId(centerId);

        centerStore.put(centerId, center);
        slotStore.put(centerId, new ArrayList<>());

        return "Center created with ID: " + centerId;
    }

    @Override
    public String createSlot(String centerId, Slot slot) {

        // Check if center exists
        GymCenter center = centerStore.get(centerId);
        if (center == null) {
            throw new CenterNotFoundException("Center " + centerId + " not found");
        }

        List<Slot> slots = slotStore.get(centerId);

        // Slot time validation
        if (!slot.getEndTime().isAfter(slot.getStartTime())) {
            throw new InvalidSlotTimeException(
                    "End time must be after start time"
            );
        }

        // Capacity validation
        if (slot.getCapacity() <= 0) {
            throw new CapacityInvalidException("Capacity must be greater than 0");
        }

        // Check overlapping or duplicate time slot
        boolean overlap = slots.stream().anyMatch(existingSlot ->
                timesOverlap(
                        existingSlot.getStartTime(),
                        existingSlot.getEndTime(),
                        slot.getStartTime(),
                        slot.getEndTime()
                )
        );

        if (overlap) {
            throw new SlotAlreadyExistsException(
                    "A slot already exists in this time range"
            );
        }

        // Assign slot ID + initial values
        String slotId = UUID.randomUUID().toString();
        slot.setSlotId(slotId);
        slot.setCenterId(centerId);
        slot.setAvailableSeats(slot.getCapacity());
        slot.setStatus("AVAILABLE");

        slots.add(slot);

        return "Slot created for center " + centerId + " with ID: " + slotId;
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

    // Utility: time overlap check
    private boolean timesOverlap(LocalTime start1, LocalTime end1,
                                 LocalTime start2, LocalTime end2) {

        return !start1.isAfter(end2) && !start2.isAfter(end1);
    }
}
