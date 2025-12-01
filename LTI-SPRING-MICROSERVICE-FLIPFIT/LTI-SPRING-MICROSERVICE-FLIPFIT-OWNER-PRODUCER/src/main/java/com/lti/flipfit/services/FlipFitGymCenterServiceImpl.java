package com.lti.flipfit.services;

/**
 * Author : 
 * Version : 1.0
 * Description : Implementation of the FlipFitGymCenterService interface.
 */

import com.lti.flipfit.entity.*;
import com.lti.flipfit.exceptions.center.*;
import com.lti.flipfit.repository.*;
import com.lti.flipfit.validator.CenterValidator;
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
    private final CenterValidator centerValidator;

    public FlipFitGymCenterServiceImpl(FlipFitGymCenterRepository centerRepo,
            FlipFitGymSlotRepository slotRepo,
            CenterValidator centerValidator) {
        this.centerRepo = centerRepo;
        this.slotRepo = slotRepo;
        this.centerValidator = centerValidator;
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

        GymCenter center = centerRepo.findById(centerId)
                .orElseThrow(() -> new CenterNotFoundException("Center " + centerId + " not found"));

        centerValidator.validateGetSlotsByDate(date, center);

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
