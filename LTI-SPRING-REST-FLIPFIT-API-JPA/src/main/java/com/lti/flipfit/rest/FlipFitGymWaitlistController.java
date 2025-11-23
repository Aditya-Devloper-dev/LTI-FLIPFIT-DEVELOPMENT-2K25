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

    /*
     * @Method: joinWaitlist
     * 
     * @Description: Adds a customer to the waitlist for a specific slot
     * 
     * @MethodParameters: customerId, slotId
     * 
     * @Exception: UserNotFoundException, SlotNotFoundException
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

    /*
     * @Method: cancelWaitlist
     * 
     * @Description: Cancels a waitlist entry
     * 
     * @MethodParameters: waitlistId
     * 
     * @Exception: BookingNotFoundException (reused for waitlist not found)
     */
    @RequestMapping(value = "/cancel/{waitlistId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> cancelWaitlist(@PathVariable String waitlistId) {
        logger.info("Received request to cancel waitlist entry: {}", waitlistId);
        if (waitlistId == null || waitlistId.isBlank()) {
            throw new InvalidInputException("Waitlist ID cannot be empty");
        }
        return ResponseEntity.ok(waitlistService.cancelWaitlist(waitlistId));
    }

    /*
     * @Method: getWaitlistByCustomer
     * 
     * @Description: Fetches all waitlist entries for a customer
     * 
     * @MethodParameters: customerId
     * 
     * @Exception: UserNotFoundException
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
