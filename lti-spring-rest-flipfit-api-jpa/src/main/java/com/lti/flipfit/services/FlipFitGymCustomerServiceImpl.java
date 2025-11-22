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
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FlipFitGymCustomerServiceImpl implements FlipFitGymCustomerService {

    private final FlipFitGymCustomerRepository customerRepo;
    private final FlipFitGymBookingRepository bookingRepo;

    public FlipFitGymCustomerServiceImpl(FlipFitGymCustomerRepository customerRepo,
            FlipFitGymBookingRepository bookingRepo) {
        this.customerRepo = customerRepo;
        this.bookingRepo = bookingRepo;
    }

    @Override
    public List<Map<String, Object>> viewAvailability(String centerId, String date) {

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
        return customerRepo.findById(customerId)
                .orElseThrow(() -> new UserNotFoundException("Customer not found"));
    }

    @Override
    public boolean validateMembership(Long customerId) {
        GymCustomer customer = getProfile(customerId);
        // Simple validation: check if status is not "EXPIRED" or "INACTIVE"
        // You can add more complex logic here
        String status = customer.getMembershipStatus();
        return status != null && !"EXPIRED".equalsIgnoreCase(status) && !"INACTIVE".equalsIgnoreCase(status);
    }

    @Override
    public List<GymBooking> getCustomerBookings(Long customerId) {
        GymCustomer customer = getProfile(customerId);
        return bookingRepo.findByCustomer(customer);
    }

}
