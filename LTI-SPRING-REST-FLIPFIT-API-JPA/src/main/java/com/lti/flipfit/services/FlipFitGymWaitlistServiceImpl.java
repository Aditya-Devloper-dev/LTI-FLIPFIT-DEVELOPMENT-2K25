package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.entity.GymWaitlist;
import com.lti.flipfit.exceptions.bookings.BookingNotFoundException;
import com.lti.flipfit.exceptions.slots.SlotNotFoundException;
import com.lti.flipfit.exceptions.user.UserNotFoundException;
import com.lti.flipfit.repository.FlipFitGymCustomerRepository;
import com.lti.flipfit.repository.FlipFitGymSlotRepository;
import com.lti.flipfit.repository.FlipFitGymWaitlistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Author :
 * Version : 1.0
 * Description : Implementation of the FlipFitGymWaitlistService interface.
 */
@Service
public class FlipFitGymWaitlistServiceImpl implements FlipFitGymWaitlistService {

        private static final Logger logger = LoggerFactory.getLogger(FlipFitGymWaitlistServiceImpl.class);

        @Autowired
        private FlipFitGymWaitlistRepository waitlistRepo;
        @Autowired
        private FlipFitGymSlotRepository slotRepo;
        @Autowired
        private FlipFitGymCustomerRepository customerRepo;

        @Override
        public String joinWaitlist(Long customerId, Long slotId) {
                logger.info("Adding customer {} to waitlist for slot {}", customerId, slotId);
                GymCustomer customer = customerRepo.findById(customerId)
                                .orElseThrow(() -> new UserNotFoundException("Customer not found"));

                GymSlot slot = slotRepo.findById(slotId)
                                .orElseThrow(() -> new SlotNotFoundException("Slot not found"));

                // Check if already in waitlist (Optional optimization)

                GymWaitlist waitlist = new GymWaitlist();
                waitlist.setWaitlistId(UUID.randomUUID().toString());
                waitlist.setCustomer(customer);
                waitlist.setSlot(slot);
                waitlist.setCreatedAt(LocalDateTime.now());
                waitlist.setPosition(1); // Logic to calculate position can be added later

                waitlistRepo.save(waitlist);

                return "Added to waitlist with ID: " + waitlist.getWaitlistId();
        }

        @Override
        public String cancelWaitlist(String waitlistId) {
                logger.info("Cancelling waitlist entry: {}", waitlistId);
                GymWaitlist waitlist = waitlistRepo.findById(waitlistId)
                                .orElseThrow(() -> new BookingNotFoundException("Waitlist entry not found"));

                waitlistRepo.delete(waitlist);
                return "Waitlist entry cancelled successfully";
        }

        @Override
        public List<GymWaitlist> getWaitlistByCustomer(Long customerId) {
                logger.info("Fetching waitlist for customer ID: {}", customerId);
                GymCustomer customer = customerRepo.findById(customerId)
                                .orElseThrow(() -> new UserNotFoundException("Customer not found"));

                // Filter by customer ID manually as we are unsure if repo has the method
                return waitlistRepo.findAll().stream()
                                .filter(w -> w.getCustomer().getCustomerId().equals(customerId))
                                .toList();
        }
}
