package com.lti.flipfit.services;

/**
 * Author :
 * Version : 1.0
 * Description : Implementation of the FlipFitGymCustomerService interface.
 */

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.exceptions.user.UserNotFoundException;
import com.lti.flipfit.repository.FlipFitGymBookingRepository;
import com.lti.flipfit.repository.FlipFitGymCustomerRepository;
import com.lti.flipfit.repository.FlipFitGymSlotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FlipFitGymCustomerServiceImpl implements FlipFitGymCustomerService {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymCustomerServiceImpl.class);

    private final FlipFitGymCustomerRepository customerRepo;
    private final FlipFitGymBookingRepository bookingRepo;
    private final FlipFitGymSlotRepository slotRepo;

    public FlipFitGymCustomerServiceImpl(FlipFitGymCustomerRepository customerRepo,
            FlipFitGymBookingRepository bookingRepo,
            FlipFitGymSlotRepository slotRepo) {
        this.customerRepo = customerRepo;
        this.bookingRepo = bookingRepo;
        this.slotRepo = slotRepo;
    }

    @Override
    public GymCustomer getProfile(Long customerId) {
        logger.info("Fetching profile for customer ID: {}", customerId);
        return customerRepo.findById(customerId)
                .orElseThrow(() -> new UserNotFoundException("Customer not found"));
    }

    @Override
    public List<GymBooking> getCustomerBookings(Long customerId) {
        logger.info("Fetching bookings for customer ID: {}", customerId);
        GymCustomer customer = getProfile(customerId);
        return bookingRepo.findByCustomer(customer);
    }

    @Override
    public List<GymSlot> getAllAvailableSlots() {
        // Fetch all active slots for display on customer home
        return slotRepo.findByIsActive(true);
    }

}
