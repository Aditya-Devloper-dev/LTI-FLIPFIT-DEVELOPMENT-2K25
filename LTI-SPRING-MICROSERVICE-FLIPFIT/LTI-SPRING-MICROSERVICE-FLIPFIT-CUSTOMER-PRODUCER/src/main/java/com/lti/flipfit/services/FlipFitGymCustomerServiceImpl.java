package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.exceptions.user.UserNotFoundException;
import com.lti.flipfit.repository.FlipFitGymBookingRepository;
import com.lti.flipfit.repository.FlipFitGymCustomerRepository;
import com.lti.flipfit.repository.FlipFitGymCenterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implementation of the FlipFitGymCustomerService interface.
 * Handles business logic for customer-related operations.
 */
@Service
public class FlipFitGymCustomerServiceImpl implements FlipFitGymCustomerService {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymCustomerServiceImpl.class);

    private final FlipFitGymCustomerRepository customerRepo;
    private final FlipFitGymBookingRepository bookingRepo;
    private final FlipFitGymCenterRepository centerRepo;

    public FlipFitGymCustomerServiceImpl(FlipFitGymCustomerRepository customerRepo,
            FlipFitGymBookingRepository bookingRepo,
            FlipFitGymCenterRepository centerRepo) {
        this.customerRepo = customerRepo;
        this.bookingRepo = bookingRepo;
        this.centerRepo = centerRepo;
    }

    /**
     * Checks the availability of slots for a given center on a specific date.
     *
     * @param centerId The ID of the gym center.
     * @param date     The date to check availability for.
     * @return A list of maps containing slot details and availability.
     */
    @Override
    public List<Map<String, Object>> viewAvailability(String centerId, String date) {
        logger.info("Checking availability for center ID: {} on date: {}", centerId, date);

        // Mock implementation for demonstration purposes
        Map<String, Object> s1 = new HashMap<>();
        s1.put("slotId", "SLOT-1");
        s1.put("startTime", "06:00");
        s1.put("endTime", "07:00");
        s1.put("availableSeats", 5);

        Map<String, Object> s2 = new HashMap<>();
        s2.put("slotId", "SLOT-2");
        s2.put("startTime", "07:00");
        s2.put("endTime", "08:00");
        s2.put("availableSeats", 0);

        return Arrays.asList(s1, s2);
    }

    /**
     * Retrieves the profile of a customer.
     *
     * @param customerId The ID of the customer.
     * @return The GymCustomer entity.
     * @throws UserNotFoundException if the customer is not found.
     */
    @Override
    public GymCustomer getProfile(Long customerId) {
        logger.info("Fetching profile for customer ID: {}", customerId);
        return customerRepo.findById(customerId)
                .orElseThrow(() -> new UserNotFoundException("Customer not found with ID: " + customerId));
    }

    /**
     * Validates the membership status of a customer.
     *
     * @param customerId The ID of the customer.
     * @return true if the membership is active, false otherwise.
     */
    @Override
    public boolean validateMembership(Long customerId) {
        logger.info("Validating membership for customer ID: {}", customerId);
        GymCustomer customer = getProfile(customerId);

        String status = customer.getMembershipStatus();
        boolean isValid = status != null &&
                !"EXPIRED".equalsIgnoreCase(status) &&
                !"INACTIVE".equalsIgnoreCase(status);

        logger.debug("Membership validity for customer ID {}: {}", customerId, isValid);
        return isValid;
    }

    /**
     * Retrieves all bookings made by a customer.
     *
     * @param customerId The ID of the customer.
     * @return A list of GymBooking entities.
     */
    @Override
    public List<GymBooking> getCustomerBookings(Long customerId) {
        logger.info("Fetching bookings for customer ID: {}", customerId);
        GymCustomer customer = getProfile(customerId);
        return bookingRepo.findByCustomer(customer);
    }

    /**
     * Retrieves all active gym centers.
     *
     * @return A list of active GymCenter entities.
     */
    @Override
    public List<com.lti.flipfit.entity.GymCenter> viewAllGyms() {
        logger.info("Fetching all active gym centers");
        return centerRepo.findByIsActiveTrue();
    }

}
