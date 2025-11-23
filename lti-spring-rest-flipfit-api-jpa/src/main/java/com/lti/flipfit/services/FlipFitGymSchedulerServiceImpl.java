package com.lti.flipfit.services;

import com.lti.flipfit.entity.*;
import com.lti.flipfit.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Author :
 * Version : 1.0
 * Description : Implementation of the FlipFitGymSchedulerService interface.
 */

@Service
public class FlipFitGymSchedulerServiceImpl implements FlipFitGymSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymSchedulerServiceImpl.class);

    private final FlipFitGymSlotRepository slotRepo;
    private final FlipFitGymWaitlistRepository waitlistRepo;
    private final FlipFitGymBookingRepository bookingRepo;

    public FlipFitGymSchedulerServiceImpl(FlipFitGymSlotRepository slotRepo,
            FlipFitGymWaitlistRepository waitlistRepo,
            FlipFitGymBookingRepository bookingRepo) {
        this.slotRepo = slotRepo;
        this.waitlistRepo = waitlistRepo;
        this.bookingRepo = bookingRepo;
    }

    @Override
    public void runWaitlistPromotionJob() {
        logger.info("Running Waitlist Promotion Job...");
        System.out.println("Running Waitlist Promotion Job...");

        // 1. Find all slots that have available seats (> 0)
        List<GymSlot> availableSlots = slotRepo.findAll().stream()
                .filter(slot -> slot.getAvailableSeats() > 0)
                .toList();

        for (GymSlot slot : availableSlots) {
            // 2. Check if there is anyone on the waitlist for this slot
            Optional<GymWaitlist> nextInLine = waitlistRepo.findFirstBySlotOrderByCreatedAtAsc(slot);

            if (nextInLine.isPresent()) {
                GymWaitlist waitlistEntry = nextInLine.get();
                logger.info("Promoting user {} for slot {}", waitlistEntry.getCustomer().getCustomerId(),
                        slot.getSlotId());
                System.out.println("Promoting user " + waitlistEntry.getCustomer().getCustomerId() + " for slot "
                        + slot.getSlotId());

                // 3. Create a confirmed booking
                GymBooking newBooking = new GymBooking();
                newBooking.setCustomer(waitlistEntry.getCustomer());
                newBooking.setSlot(slot);
                newBooking.setCenter(slot.getCenter());
                newBooking.setStatus("CONFIRMED");
                newBooking.setCreatedAt(LocalDateTime.now());
                newBooking.setOwnerApprovalRequired(false);
                newBooking.setApprovedByOwner(true); // Auto-approve promoted bookings

                bookingRepo.save(newBooking);

                // 4. Remove from waitlist
                waitlistRepo.delete(waitlistEntry);

                // 5. Decrease available seats
                slot.setAvailableSeats(slot.getAvailableSeats() - 1);
                slotRepo.save(slot);
            }
        }
        logger.info("Waitlist Promotion Job Completed.");
        System.out.println("Waitlist Promotion Job Completed.");
    }

    @Override
    public void sendDailyReminders() {
        logger.info("Dummy: Daily reminder job executed.");
        System.out.println("Dummy: Daily reminder job executed.");
    }
}
