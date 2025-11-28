package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.exceptions.user.UserNotFoundException;
import com.lti.flipfit.repository.FlipFitGymCustomerRepository;
import com.lti.flipfit.dao.FlipFitGymCustomerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
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
    private final FlipFitGymCustomerDAO customerDAO;

    public FlipFitGymCustomerServiceImpl(FlipFitGymCustomerRepository customerRepo,
            FlipFitGymCustomerDAO customerDAO) {
        this.customerRepo = customerRepo;
        this.customerDAO = customerDAO;
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
    @Cacheable(value = "gymAvailability")
    public List<Map<String, Object>> viewAvailability(String centerId, String date) {
        logger.info("Checking availability for center ID: {} on date: {}", centerId, date);

        Long cId = Long.parseLong(centerId);
        java.time.LocalDate bookingDate = java.time.LocalDate.parse(date);

        List<Object[]> results = customerDAO.findSlotAvailability(cId, bookingDate);
        List<Map<String, Object>> availabilityList = new ArrayList<>();

        for (Object[] result : results) {
            com.lti.flipfit.entity.GymSlot slot = (com.lti.flipfit.entity.GymSlot) result[0];
            Long bookedCount = (Long) result[1];
            int availableSeats = slot.getCapacity() - bookedCount.intValue();

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
    @Cacheable(value = "customerProfile", key = "#customerId")
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
    @Cacheable(value = "customerBookings", key = "#customerId")
    public List<GymBooking> getCustomerBookings(Long customerId) {
        logger.info("Fetching bookings for customer ID: {}", customerId);
        return customerDAO.findBookingsByCustomerId(customerId);
    }

    /**
     * @methodname - viewAllGyms
     * @description - Retrieves all active gym centers.
     * @return - A list of active GymCenter entities.
     */
    @Override
    @Cacheable(value = "gymCenters")
    public List<com.lti.flipfit.entity.GymCenter> viewAllGyms() {
        logger.info("Fetching all active gym centers");
        return customerDAO.findActiveGyms();
    }

}
