package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.entity.GymSlot;
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

    @Override
    public List<GymSlot> getAllAvailableSlots() {
        // Fetch all active slots for display on customer home
        List<GymSlot> slots = customerDAO.findAllActiveSlots();
        logger.info("Fetched {} active and approved slots from database", slots.size());
        return slots;
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

    @Override
    public GymCustomer getCustomerByUserId(Long userId) {
        return customerRepo.findByUser_UserId(userId);
    }
}
