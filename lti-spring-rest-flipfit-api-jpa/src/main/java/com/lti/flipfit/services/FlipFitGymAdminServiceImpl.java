package com.lti.flipfit.services;

/**
 * Author :
 * Version : 1.0
 * Description : Implementation of the FlipFitGymAdminService interface.
 */

import com.lti.flipfit.entity.GymAdmin;
import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.exceptions.center.CenterAlreadyExistsException;
import com.lti.flipfit.exceptions.center.CenterNotFoundException;
import com.lti.flipfit.exceptions.slots.CapacityInvalidException;
import com.lti.flipfit.exceptions.slots.InvalidSlotTimeException;
import com.lti.flipfit.exceptions.slots.SlotAlreadyExistsException;
import com.lti.flipfit.repository.FlipFitGymAdminRepository;
import com.lti.flipfit.repository.FlipFitGymCenterRepository;
import com.lti.flipfit.repository.FlipFitGymSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class FlipFitGymAdminServiceImpl implements FlipFitGymAdminService {

    private final FlipFitGymCenterRepository centerRepo;
    private final FlipFitGymSlotRepository slotRepo;
    private final FlipFitGymAdminRepository adminRepo;

    @Autowired
    public FlipFitGymAdminServiceImpl(FlipFitGymCenterRepository centerRepo,
            FlipFitGymSlotRepository slotRepo,
            FlipFitGymAdminRepository adminRepo) {
        this.centerRepo = centerRepo;
        this.slotRepo = slotRepo;
        this.adminRepo = adminRepo;
    }

    @Override
    public String createCenter(GymCenter center) {

        boolean exists = centerRepo.existsByCenterNameIgnoreCaseAndCityIgnoreCase(
                center.getCenterName(), center.getCity());

        if (exists) {
            throw new CenterAlreadyExistsException(
                    "Center already exists in city: " + center.getCity());
        }

        Long adminId = center.getAdmin().getAdminId();
        GymAdmin admin = adminRepo.findById(adminId)
                .orElseThrow(() -> new InvalidInputException("Invalid admin ID: " + adminId));

        center.setAdmin(admin);

        center.setCreatedAt(LocalDateTime.now());
        center.setUpdatedAt(LocalDateTime.now());

        GymCenter saved = centerRepo.save(center);

        return "Center created with ID: " + saved.getCenterId();
    }

    @Override
    public String createSlot(Long centerId, GymSlot slot) {
        GymCenter center = centerRepo.findById(centerId)
                .orElseThrow(() -> new CenterNotFoundException(
                        "Center " + centerId + " not found"));

        if (!slot.getEndTime().isAfter(slot.getStartTime())) {
            throw new InvalidSlotTimeException("End time must be after start time");
        }

        if (slot.getCapacity() <= 0) {
            throw new CapacityInvalidException("Capacity must be greater than 0");
        }

        List<GymSlot> existingSlots = slotRepo.findByCenterCenterId(centerId);

        boolean overlap = existingSlots.stream().anyMatch(existing -> timesOverlap(
                existing.getStartTime(),
                existing.getEndTime(),
                slot.getStartTime(),
                slot.getEndTime()));

        if (overlap) {
            throw new SlotAlreadyExistsException("A slot already exists in this time range");
        }

        slot.setCenter(center);
        slot.setAvailableSeats(slot.getCapacity());
        slot.setStatus("AVAILABLE");

        GymSlot saved = slotRepo.save(slot);

        return "Slot created with ID: " + saved.getSlotId() +
                " for center " + centerId;
    }

    @Override
    public List<GymCenter> getAllCenters() {
        return centerRepo.findAll();
    }

    @Override
    public GymCenter getCenterById(Long centerId) {
        return centerRepo.findById(centerId)
                .orElseThrow(() -> new CenterNotFoundException(
                        "Center " + centerId + " not found"));
    }

    private boolean timesOverlap(LocalTime s1, LocalTime e1, LocalTime s2, LocalTime e2) {
        return s1.isBefore(e2) && s2.isBefore(e1);
    }
}
