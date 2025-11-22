package com.lti.flipfit.rest;

import com.lti.flipfit.services.FlipFitGymCustomerService;
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
     * @Method: Booking a slot for a customer
     * 
     * @Description: Books a slot for the given customer after validating center and
     * slot
     * 
     * @MethodParameters: String customerId, String slotId, String centerId
     * 
     * @Exception: Throws exceptions for conflicts, full slots, or invalid inputs
     */

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public String bookSlot(
            @RequestParam String customerId,
            @RequestParam String slotId,
            @RequestParam String centerId) {

        return customerService.bookSlot(customerId, slotId, centerId);
    }

    /*
     * @Method: Cancelling a customer booking
     * 
     * @Description: Cancels the booking associated with the given bookingId
     * 
     * @MethodParameters: String bookingId
     * 
     * @Exception: Throws exceptions if booking is not found or cancellation rules
     * fail
     */

    @RequestMapping(value = "/cancel/{bookingId}", method = RequestMethod.DELETE)
    public boolean cancelBooking(@PathVariable String bookingId) {
        return customerService.cancelBooking(bookingId);
    }
}
