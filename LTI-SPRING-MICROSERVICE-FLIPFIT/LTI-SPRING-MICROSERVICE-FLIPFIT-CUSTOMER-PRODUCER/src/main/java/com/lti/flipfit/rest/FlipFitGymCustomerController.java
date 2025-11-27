package com.lti.flipfit.rest;

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.services.FlipFitGymCustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Author :
 * Version : 1.0
 * Description : Controller for customer actions like searching gym
 * availability, booking slots, and cancelling their own bookings.
 */
@RestController
@RequestMapping("/customer")
public class FlipFitGymCustomerController {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymCustomerController.class);

    private final FlipFitGymCustomerService customerService;

    public FlipFitGymCustomerController(FlipFitGymCustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * @methodname - viewAvailability
     * @description - Fetches availability details for a given center on a specific
     *              date.
     * @param - centerId The ID of the gym center.
     * @param - date The date to check availability for.
     * @return - A list of maps containing slot details and availability.
     */
    @RequestMapping(value = "/availability", method = RequestMethod.GET)
    public List<Map<String, Object>> viewAvailability(
            @RequestParam String centerId,
            @RequestParam String date) {
        logger.info("Received request to view availability for center ID: {} on date: {}", centerId, date);
        return customerService.viewAvailability(centerId, date);
    }

    /**
     * @methodname - getProfile
     * @description - Fetches customer profile details.
     * @param - customerId The unique ID of the customer.
     * @return - The GymCustomer entity.
     * @throws InvalidInputException if the customer ID is null.
     */
    @RequestMapping(value = "/profile/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<GymCustomer> getProfile(@PathVariable Long customerId) {
        logger.info("Received request to get profile for customer ID: {}", customerId);
        if (customerId == null) {
            throw new InvalidInputException("Customer ID cannot be empty");
        }
        return ResponseEntity.ok(customerService.getProfile(customerId));
    }

    /**
     * @methodname - getCustomerBookings
     * @description - Fetches all bookings made by the customer.
     * @param - customerId The unique ID of the customer.
     * @return - A list of GymBooking entities.
     * @throws InvalidInputException if the customer ID is null.
     */
    @RequestMapping(value = "/bookings/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<List<GymBooking>> getCustomerBookings(@PathVariable Long customerId) {
        logger.info("Received request to get bookings for customer ID: {}", customerId);
        if (customerId == null) {
            throw new InvalidInputException("Customer ID cannot be empty");
        }
        return ResponseEntity.ok(customerService.getCustomerBookings(customerId));
    }

    /**
     * @methodname - viewAllGyms
     * @description - Fetches all active gym centers.
     * @return - A list of active GymCenter entities.
     */
    @RequestMapping(value = "/view-gyms", method = RequestMethod.GET)
    public ResponseEntity<List<com.lti.flipfit.entity.GymCenter>> viewAllGyms() {
        logger.info("Received request to view all active gym centers");
        return ResponseEntity.ok(customerService.viewAllGyms());
    }
}
