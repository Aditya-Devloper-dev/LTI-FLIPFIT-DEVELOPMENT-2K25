package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymOwner;
import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.exceptions.bookings.BookingNotFoundException;
import com.lti.flipfit.exceptions.center.CenterNotFoundException;
import com.lti.flipfit.exceptions.user.UserNotFoundException;
import com.lti.flipfit.repository.FlipFitGymBookingRepository;
import com.lti.flipfit.repository.FlipFitGymCenterRepository;
import com.lti.flipfit.repository.FlipFitGymOwnerRepository;
import com.lti.flipfit.repository.FlipFitGymSlotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author :
 * Version : 1.0
 * Description : Implementation of the FlipFitGymOwnerService interface.
 */
@Service
public class FlipFitGymOwnerServiceImpl implements FlipFitGymOwnerService {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymOwnerServiceImpl.class);

    private final FlipFitGymOwnerRepository ownerRepo;
    private final FlipFitGymCenterRepository centerRepo;
    private final FlipFitGymBookingRepository bookingRepo;
    private final FlipFitGymSlotRepository slotRepo;

    public FlipFitGymOwnerServiceImpl(FlipFitGymOwnerRepository ownerRepo,
            FlipFitGymCenterRepository centerRepo,
            FlipFitGymBookingRepository bookingRepo,
            FlipFitGymSlotRepository slotRepo) {
        this.ownerRepo = ownerRepo;
        this.centerRepo = centerRepo;
        this.bookingRepo = bookingRepo;
        this.slotRepo = slotRepo;
    }

    @Override
    public boolean approveBooking(Long bookingId) {
        logger.info("Approving booking with ID: {}", bookingId);
        GymBooking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        booking.setApprovedByOwner(true);
        bookingRepo.save(booking);
        return true;
    }

    @Override
    public GymCenter addCenter(GymCenter center, Long ownerId) {
        logger.info("Adding center for owner ID: {}", ownerId);
        GymOwner owner = ownerRepo.findById(ownerId)
                .orElseThrow(() -> new UserNotFoundException("Owner not found"));

        center.setOwner(owner);
        return centerRepo.save(center);
    }

    @Override
    public GymCenter updateCenter(GymCenter center) {
        logger.info("Updating center with ID: {}", center.getCenterId());
        if (center.getCenterId() == null) {
            throw new CenterNotFoundException("Center ID is required for update");
        }

        GymCenter existingCenter = centerRepo.findById(center.getCenterId())
                .orElseThrow(() -> new CenterNotFoundException("Center not found"));

        // Update fields (only non-null)
        if (center.getCenterName() != null)
            existingCenter.setCenterName(center.getCenterName());
        if (center.getCity() != null)
            existingCenter.setCity(center.getCity());
        if (center.getContactNumber() != null)
            existingCenter.setContactNumber(center.getContactNumber());
        // Don't update Owner or Admin unless explicitly needed logic is added

        return centerRepo.save(existingCenter);
    }

    @Override
    public List<GymBooking> viewAllBookings(Long centerId) {
        logger.info("Fetching all bookings for center ID: {}", centerId);
        if (!centerRepo.existsById(centerId)) {
            throw new CenterNotFoundException("Center not found");
        }
        return bookingRepo.findByCenterCenterId(centerId);
    }

    @Override
    public List<GymCenter> getCentersByOwner(Long ownerId) {
        logger.info("Fetching centers for owner ID: {}", ownerId);
        if (!ownerRepo.existsById(ownerId)) {
            throw new UserNotFoundException("Owner not found");
        }
        return centerRepo.findByOwnerOwnerId(ownerId);
    }

    @Override
    public void addSlot(GymSlot slot, Long centerId) {
        logger.info("Adding slot to center ID: {}", centerId);
        GymCenter center = centerRepo.findById(centerId)
                .orElseThrow(() -> new CenterNotFoundException("Center not found"));

        slot.setCenter(center);
        slot.setIsActive(false); // Default to inactive until approved
        slot.setAvailableSeats(slot.getCapacity());
        slot.setStatus("PENDING");

        slotRepo.save(slot);
    }
}
