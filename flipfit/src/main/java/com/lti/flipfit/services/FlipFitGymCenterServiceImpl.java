package com.lti.flipfit.services;

import com.lti.flipfit.beans.GymCenter;
import com.lti.flipfit.beans.Slot;
import com.lti.flipfit.exceptions.CenterExceptions.CenterNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class FlipFitGymCenterServiceImpl implements FlipFitGymCenterService {

    private final Map<String, GymCenter> centerStore = new HashMap<>();
    private final Map<String, List<Slot>> slotStore = new HashMap<>();

    @Override
    public List<Slot> getSlotsByDate(String centerId, LocalDate date) {

        GymCenter center = centerStore.get(centerId);
        if (center == null) {
            throw new CenterNotFoundException("Center " + centerId + " not found");
        }

        List<Slot> slots = slotStore.get(centerId);
        if (slots == null) {
            return Collections.emptyList();
        }

        // For now, return all slots (since you aren't storing slots by date)
        return slots;
    }

    @Override
    public boolean updateCenterInfo(String centerId, GymCenter updatedCenter) {

        GymCenter existingCenter = centerStore.get(centerId);

        if (existingCenter == null) {
            throw new CenterNotFoundException("Center " + centerId + " not found");
        }

        // Update fields
        if (updatedCenter.getCenterName() != null && !updatedCenter.getCenterName().isBlank()) {
            existingCenter.setCenterName(updatedCenter.getCenterName());
        }

        if (updatedCenter.getCity() != null && !updatedCenter.getCity().isBlank()) {
            existingCenter.setCity(updatedCenter.getCity());
        }

        if (updatedCenter.getContactNumber() != null && !updatedCenter.getContactNumber().isBlank()) {
            existingCenter.setContactNumber(updatedCenter.getContactNumber());
        }

        existingCenter.setActive(updatedCenter.isActive());

        return true;
    }
}
