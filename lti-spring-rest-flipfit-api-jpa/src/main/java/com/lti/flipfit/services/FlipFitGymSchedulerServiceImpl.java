package com.lti.flipfit.services;

/**
 * Author :
 * Version : 1.0
 * Description : Implementation of the FlipFitGymSchedulerService interface.
 */

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.entity.GymWaitlist;
import com.lti.flipfit.repository.FlipFitGymBookingRepository;
import com.lti.flipfit.repository.FlipFitGymSlotRepository;
import com.lti.flipfit.repository.FlipFitGymWaitlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FlipFitGymSchedulerServiceImpl implements FlipFitGymSchedulerService {

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
    @Transactional
    public void runWaitlistPromotionJob() {
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
        System.out.println("Waitlist Promotion Job Completed.");
    }

    @Override
    public void sendDailyReminders() {
        System.out.println("Dummy: Daily reminder job executed.");
    }
}
