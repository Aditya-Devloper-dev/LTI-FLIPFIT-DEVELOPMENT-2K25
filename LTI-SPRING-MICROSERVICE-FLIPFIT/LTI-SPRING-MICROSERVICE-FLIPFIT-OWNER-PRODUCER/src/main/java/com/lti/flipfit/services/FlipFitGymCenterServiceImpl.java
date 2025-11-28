package com.lti.flipfit.services;

/**
 * Author : 
 * Version : 1.0
 * Description : Implementation of the FlipFitGymCenterService interface.
 */

import com.lti.flipfit.entity.*;
import com.lti.flipfit.exceptions.center.*;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

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
    @Cacheable(value = "centerSlots", key = "#centerId + '-' + #date")
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
    @Cacheable(value = "centerSlots", key = "#centerId")
    public List<GymSlot> getSlotsByCenterId(Long centerId) {
        logger.info("Fetching all slots for center ID: {}", centerId);
        if (!centerRepo.existsById(centerId)) {
            throw new CenterNotFoundException("Center " + centerId + " not found");
        }
        return slotRepo.findByCenterCenterId(centerId);
    }

}
