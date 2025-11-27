package com.lti.flipfit.services;

/**
 * Author : 
 * Version : 1.0
 * Description : Implementation of the FlipFitGymCenterService interface.
 */

import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.exceptions.center.CenterNotFoundException;
import com.lti.flipfit.exceptions.center.CenterUpdateNotAllowedException;
import com.lti.flipfit.exceptions.center.InvalidCenterLocationException;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.repository.FlipFitGymCenterRepository;
import com.lti.flipfit.repository.FlipFitGymSlotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FlipFitGymCenterServiceImpl implements FlipFitGymCenterService {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymCenterServiceImpl.class);

    private final FlipFitGymCenterRepository centerRepo;
    private final FlipFitGymSlotRepository slotRepo;

    public FlipFitGymCenterServiceImpl(FlipFitGymCenterRepository centerRepo,
            FlipFitGymSlotRepository slotRepo) {
        this.centerRepo = centerRepo;
        this.slotRepo = slotRepo;
    }

    /**
     * @methodname - getSlotsByDate
     * @description - Fetches all slots for a given center on a specific date.
     * @param - centerId The ID of the gym center.
     * @param - date The date to check availability for.
     * @return - A list of GymSlot entities.
     * @throws CenterNotFoundException if center does not exist.
     */
    @Override
    public List<GymSlot> getSlotsByDate(Long centerId, String date) {
        logger.info("Fetching slots for center ID: {} on date: {}", centerId, date);

        if (date == null || date.isBlank()) {
            throw new InvalidInputException("Date cannot be empty");
        }

        try {
        } catch (Exception e) {
            throw new InvalidInputException("Invalid date format. Expected yyyy-MM-dd");
        }

        GymCenter center = centerRepo.findById(centerId)
                .orElseThrow(() -> new CenterNotFoundException("Center " + centerId + " not found"));

        if (!Boolean.TRUE.equals(center.getIsActive())) {
            throw new CenterUpdateNotAllowedException("Center is inactive");
        }

        List<GymSlot> slots = slotRepo.findByCenterCenterId(centerId);

        // If you want to match date manually (ONLY IF NEEDED)
        // return slots.stream()
        // .filter(s -> s.getStartTime().toLocalDate().isEqual(date))
        // .toList();

        return slots;
    }

    /**
     * @methodname - getSlotsByCenterId
     * @description - Fetches all slots for a given center.
     * @param - centerId The ID of the gym center.
     * @return - A list of GymSlot entities.
     * @throws CenterNotFoundException if center does not exist.
     */
    @Override
    public List<GymSlot> getSlotsByCenterId(Long centerId) {
        logger.info("Fetching all slots for center ID: {}", centerId);
        if (!centerRepo.existsById(centerId)) {
            throw new CenterNotFoundException("Center " + centerId + " not found");
        }
        return slotRepo.findByCenterCenterId(centerId);
    }

    /**
     * @methodname - updateCenterInfo
     * @description - Updates gym center details after validating allowed fields.
     * @param - centerId The ID of the center.
     * @param - updatedCenter The updated center payload.
     * @return - True if update is successful.
     * @throws CenterNotFoundException, InvalidCenterLocationException,
     *                                  CenterUpdateNotAllowedException
     */
    @Override
    public boolean updateCenterInfo(Long centerId, GymCenter updatedCenter) {
        logger.info("Updating center info for center ID: {}", centerId);

        GymCenter existingCenter = centerRepo.findById(centerId)
                .orElseThrow(() -> new CenterNotFoundException("Center " + centerId + " not found"));

        // Prevent changing centerId
        if (updatedCenter.getCenterId() != null &&
                !updatedCenter.getCenterId().equals(centerId)) {
            throw new CenterUpdateNotAllowedException("centerId cannot be changed");
        }

        // Validate city name
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

        existingCenter.setUpdatedAt(java.time.LocalDateTime.now());

        centerRepo.save(existingCenter);
        return true;
    }
}
