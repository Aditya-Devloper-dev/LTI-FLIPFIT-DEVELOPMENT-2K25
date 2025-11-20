package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.exceptions.center.CenterNotFoundException;
import com.lti.flipfit.exceptions.center.CenterUpdateNotAllowedException;
import com.lti.flipfit.exceptions.center.InvalidCenterLocationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class FlipFitGymCenterServiceImpl implements FlipFitGymCenterService {

    // In-memory stores (since DAO not used yet)
    private final Map<String, GymCenter> centerStore = new HashMap<>();
    private final Map<String, List<GymSlot>> slotStore = new HashMap<>();
    /*
     * @Method: getSlotsByDate
     * @Description: Fetches all slots for a given center on a specific date.
     * @MethodParameters: centerId -> ID of the gym center, date -> selected date.
     * @Exception: Throws CenterNotFoundException if center does not exist.
     */
    @Override
    public List<GymSlot> getSlotsByDate(String centerId, LocalDate date) {

        GymCenter center = centerStore.get(centerId);

        if (center == null) {
            throw new CenterNotFoundException("Center " + centerId + " not found");
        }

        if (!Boolean.TRUE.equals(center.getIsActive())) {
            throw new CenterUpdateNotAllowedException("Center is inactive, cannot fetch slots");
        }

        List<GymSlot> slots = slotStore.get(centerId);

        return (slots == null) ? Collections.emptyList() : slots;
    }



    /*
     * @Method: updateCenterInfo
     * @Description: Updates gym center details after validating allowed fields.
     * @MethodParameters: centerId -> ID of the center, updatedCenter -> updated center payload
     * @Exception: Throws CenterNotFoundException, InvalidCenterLocationException, CenterUpdateNotAllowedException
     */
    @Override
    public boolean updateCenterInfo(String centerId, GymCenter updatedCenter) {

        GymCenter existingCenter = centerStore.get(centerId);

        if (existingCenter == null) {
            throw new CenterNotFoundException("Center " + centerId + " not found");
        }

        // Center ID cannot change
        if (updatedCenter.getCenterId() != null &&
                !updatedCenter.getCenterId().equals(centerId)) {
            throw new CenterUpdateNotAllowedException("centerId cannot be changed");
        }

        // Validate location if updated
        if (updatedCenter.getCity() != null &&
                updatedCenter.getCity().trim().length() < 3) {
            throw new InvalidCenterLocationException("Invalid city name provided");
        }

        // Update allowed fields
        if (updatedCenter.getCenterName() != null && !updatedCenter.getCenterName().isBlank()) {
            existingCenter.setCenterName(updatedCenter.getCenterName());
        }

        if (updatedCenter.getCity() != null && !updatedCenter.getCity().isBlank()) {
            existingCenter.setCity(updatedCenter.getCity());
        }

        if (updatedCenter.getContactNumber() != null && !updatedCenter.getContactNumber().isBlank()) {
            existingCenter.setContactNumber(updatedCenter.getContactNumber());
        }

        if (updatedCenter.getIsActive() != null) {
            existingCenter.setIsActive(updatedCenter.getIsActive());
        }

        return true;
    }
}
