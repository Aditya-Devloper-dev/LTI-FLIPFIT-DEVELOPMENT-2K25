package com.lti.flipfit.rest;

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.services.FlipFitGymCustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Author :
 * Version : 1.0
 * Description : Controller for customer actions like searching gym
 * availability,
 * booking slots, and cancelling their own bookings.
 */

@RestController
@RequestMapping("/customer")
public class FlipFitGymCustomerController {

    private final FlipFitGymCustomerService customerService;

    public FlipFitGymCustomerController(FlipFitGymCustomerService customerService) {
        this.customerService = customerService;
    }

    /*
     * @Method: Viewing slot availability
     * 
     * @Description: Fetches availability details for a given center on a specific
     * date
     * 
     * @MethodParameters: String centerId, String date
     * 
     * @Exception: Throws exceptions if centerId/date is invalid or data retrieval
     * fails
     */

    @RequestMapping(value = "/availability", method = RequestMethod.GET)
    public List<Map<String, Object>> viewAvailability(
            @RequestParam String centerId,
            @RequestParam String date) {

        return customerService.viewAvailability(centerId, date);
    }

    /*
     * @Method: getProfile
     * 
     * @Description: Fetches customer profile details
     * 
     * @MethodParameters: customerId -> unique customer ID
     * 
     * @Exception: Throws UserNotFoundException if customer not found
     */
    @RequestMapping(value = "/profile/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<GymCustomer> getProfile(@PathVariable Long customerId) {
        if (customerId == null) {
            throw new InvalidInputException("Customer ID cannot be empty");
        }
        return ResponseEntity.ok(customerService.getProfile(customerId));
    }

    /*
     * @Method: validateMembership
     * 
     * @Description: Checks if the customer's membership is active
     * 
     * @MethodParameters: customerId -> unique customer ID
     * 
     * @Exception: Throws UserNotFoundException if customer not found
     */
    @RequestMapping(value = "/validate-membership/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> validateMembership(@PathVariable Long customerId) {
        if (customerId == null) {
            throw new InvalidInputException("Customer ID cannot be empty");
        }
        return ResponseEntity.ok(customerService.validateMembership(customerId));
    }

    /*
     * @Method: getCustomerBookings
     * 
     * @Description: Fetches all bookings made by the customer
     * 
     * @MethodParameters: customerId -> unique customer ID
     * 
     * @Exception: Throws UserNotFoundException if customer not found
     */
    @RequestMapping(value = "/bookings/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<List<GymBooking>> getCustomerBookings(@PathVariable Long customerId) {
        if (customerId == null) {
            throw new InvalidInputException("Customer ID cannot be empty");
        }
        return ResponseEntity.ok(customerService.getCustomerBookings(customerId));
    }
}
