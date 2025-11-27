package com.lti.flipfit.rest;

import com.lti.flipfit.entity.GymWaitlist;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.services.FlipFitGymWaitlistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author :
 * Version : 1.0
 * Description : Controller for managing waitlist operations.
 */
@RestController
@RequestMapping("/waitlist")
public class FlipFitGymWaitlistController {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymWaitlistController.class);

    private final FlipFitGymWaitlistService waitlistService;

    public FlipFitGymWaitlistController(FlipFitGymWaitlistService waitlistService) {
        this.waitlistService = waitlistService;
    }

    /**
     * @methodname - joinWaitlist
     * @description - Adds a customer to the waitlist for a specific slot.
     * @param - customerId The ID of the customer.
     * @param - slotId The ID of the slot.
     * @return - A response entity containing the waitlist status.
     */
    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public ResponseEntity<String> joinWaitlist(
            @RequestParam Long customerId,
            @RequestParam Long slotId) {
        logger.info("Received request to join waitlist. Customer ID: {}, Slot ID: {}", customerId, slotId);

        if (customerId == null || slotId == null) {
            throw new InvalidInputException("Customer ID and Slot ID are required");
        }
        return ResponseEntity.ok(waitlistService.joinWaitlist(customerId, slotId));
    }

    /**
     * @methodname - cancelWaitlist
     * @description - Cancels a waitlist entry.
     * @param - waitlistId The unique identifier of the waitlist entry.
     * @return - A response entity containing the cancellation status.
     */
    @RequestMapping(value = "/cancel/{waitlistId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> cancelWaitlist(@PathVariable String waitlistId) {
        logger.info("Received request to cancel waitlist entry: {}", waitlistId);
        if (waitlistId == null || waitlistId.isBlank()) {
            throw new InvalidInputException("Waitlist ID cannot be empty");
        }
        return ResponseEntity.ok(waitlistService.cancelWaitlist(waitlistId));
    }

    /**
     * @methodname - getWaitlistByCustomer
     * @description - Fetches all waitlist entries for a customer.
     * @param - customerId The ID of the customer.
     * @return - A response entity containing a list of waitlist entries.
     */
    @RequestMapping(value = "/view/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<List<GymWaitlist>> getWaitlistByCustomer(@PathVariable Long customerId) {
        logger.info("Received request to view waitlist for customer ID: {}", customerId);
        if (customerId == null) {
            throw new InvalidInputException("Customer ID cannot be empty");
        }
        return ResponseEntity.ok(waitlistService.getWaitlistByCustomer(customerId));
    }
}
