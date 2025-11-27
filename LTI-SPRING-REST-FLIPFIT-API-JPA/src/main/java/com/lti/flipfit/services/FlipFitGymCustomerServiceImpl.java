package com.lti.flipfit.services;

/**
 * Author :
 * Version : 1.0
 * Description : Implementation of the FlipFitGymCustomerService interface.
 */

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.exceptions.user.UserNotFoundException;
import com.lti.flipfit.repository.FlipFitGymBookingRepository;
import com.lti.flipfit.repository.FlipFitGymCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FlipFitGymCustomerServiceImpl implements FlipFitGymCustomerService {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymCustomerServiceImpl.class);

    private final FlipFitGymCustomerRepository customerRepo;
    private final FlipFitGymBookingRepository bookingRepo;

    public FlipFitGymCustomerServiceImpl(FlipFitGymCustomerRepository customerRepo,
            FlipFitGymBookingRepository bookingRepo) {
        this.customerRepo = customerRepo;
        this.bookingRepo = bookingRepo;
    }

    @Override
    public List<Map<String, Object>> viewAvailability(String centerId, String date) {
        logger.info("Checking availability for center ID: {} on date: {}", centerId, date);

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

}
