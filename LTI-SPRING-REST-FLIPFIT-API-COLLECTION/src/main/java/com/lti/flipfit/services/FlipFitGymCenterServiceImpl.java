package com.lti.flipfit.services;

import com.lti.flipfit.beans.GymCenter;
import com.lti.flipfit.beans.Slot;
import com.lti.flipfit.dao.GymCenterFlipFitDao;
import com.lti.flipfit.exceptions.center.CenterNotFoundException;
import com.lti.flipfit.exceptions.center.CenterUpdateNotAllowedException;
import com.lti.flipfit.exceptions.center.InvalidCenterLocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class FlipFitGymCenterServiceImpl implements FlipFitGymCenterService {

    @Autowired
    private GymCenterFlipFitDao flipFitGymCenterDao;

    /*
     * @Method: getSlotsByDate
     * @Description: Fetches all slots for a given center on a specific date.
     * @MethodParameters: centerId -> ID of the gym center, date -> selected date.
     * @Exception: Throws CenterNotFoundException if center does not exist.
     */
    @Override
    public List<Slot> getSlotsByDate(String centerId, LocalDate date) {

        GymCenter center = flipFitGymCenterDao.centerStore.get(centerId);;

        if (center == null) {
            throw new CenterNotFoundException("Center " + centerId + " not found");
        }

        // Optional rule: center must be active
        if (!center.isActive()) {
            throw new CenterUpdateNotAllowedException("Center is inactive, cannot fetch slots");
        }

        List<Slot> slots = flipFitGymCenterDao.slotStore.get(centerId);;
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

        GymCenter existingCenter = flipFitGymCenterDao.centerStore.get(centerId);


        if (existingCenter == null) {
            throw new CenterNotFoundException("Center " + centerId + " not found");
        }

        // Cannot modify immutable fields
        if (updatedCenter.getCenterId() != null &&
                !updatedCenter.getCenterId().equals(centerId)) {
            throw new CenterUpdateNotAllowedException("centerId cannot be changed");
        }

        // Location validation if required
        if (updatedCenter.getCity() != null &&
                updatedCenter.getCity().trim().length() < 3) {
            throw new InvalidCenterLocationException("Invalid city name provided");
        }

        // Apply valid updates
        if (updatedCenter.getCenterName() != null && !updatedCenter.getCenterName().isBlank()) {
            existingCenter.setCenterName(updatedCenter.getCenterName());
        }

        if (updatedCenter.getCity() != null && !updatedCenter.getCity().isBlank()) {
            existingCenter.setCity(updatedCenter.getCity());
        }

        if (updatedCenter.getContactNumber() != null &&
                !updatedCenter.getContactNumber().isBlank()) {
            existingCenter.setContactNumber(updatedCenter.getContactNumber());
        }

        existingCenter.setActive(updatedCenter.isActive());

        return true;
    }
}
