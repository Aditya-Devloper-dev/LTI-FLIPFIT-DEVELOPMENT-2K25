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
 * Author :
 * Version : 1.0
 * Description : Implementation of the FlipFitGymCustomerService interface.
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
     * @methodname - viewAvailability
     * @description - Checks the availability of slots for a given center on a
     *              specific date.
     * @param - centerId The ID of the gym center.
     * @param - date The date to check availability for.
     * @return - A list of maps containing slot details and availability.
     */
    @Override
    public List<Map<String, Object>> viewAvailability(String centerId, String date) {
        logger.info("Checking availability for center ID: {} on date: {}", centerId, date);

        Long cId = Long.parseLong(centerId);
        java.time.LocalDate bookingDate = java.time.LocalDate.parse(date);

        com.lti.flipfit.entity.GymCenter center = centerRepo.findById(cId)
                .orElseThrow(() -> new com.lti.flipfit.exceptions.InvalidInputException("Center not found"));

        List<Map<String, Object>> availabilityList = new ArrayList<>();

        for (com.lti.flipfit.entity.GymSlot slot : center.getSlots()) {
            if (!slot.getIsActive())
                continue;

            int bookedCount = bookingRepo.countBySlotAndBookingDate(slot, bookingDate);
            int availableSeats = slot.getCapacity() - bookedCount;

            Map<String, Object> slotDetails = new HashMap<>();
            slotDetails.put("slotId", slot.getSlotId());
            slotDetails.put("startTime", slot.getStartTime());
            slotDetails.put("endTime", slot.getEndTime());
            slotDetails.put("availableSeats", Math.max(0, availableSeats));
            slotDetails.put("price", slot.getPrice());

            availabilityList.add(slotDetails);
        }

        return availabilityList;
    }

    /**
     * @methodname - getProfile
     * @description - Retrieves the profile of a customer.
     * @param - customerId The ID of the customer.
     * @return - The GymCustomer entity.
     * @throws UserNotFoundException if the customer is not found.
     */
    @Override
    public GymCustomer getProfile(Long customerId) {
        logger.info("Fetching profile for customer ID: {}", customerId);
        return customerRepo.findById(customerId)
                .orElseThrow(() -> new UserNotFoundException("Customer not found with ID: " + customerId));
    }

    /**
     * @methodname - getCustomerBookings
     * @description - Retrieves all bookings made by a customer.
     * @param - customerId The ID of the customer.
     * @return - A list of GymBooking entities.
     */
    @Override
    public List<GymBooking> getCustomerBookings(Long customerId) {
        logger.info("Fetching bookings for customer ID: {}", customerId);
        GymCustomer customer = getProfile(customerId);
        return bookingRepo.findByCustomer(customer);
    }

    /**
     * @methodname - viewAllGyms
     * @description - Retrieves all active gym centers.
     * @return - A list of active GymCenter entities.
     */
    @Override
    public List<com.lti.flipfit.entity.GymCenter> viewAllGyms() {
        logger.info("Fetching all active gym centers");
        return centerRepo.findByIsActiveTrue();
    }

}
