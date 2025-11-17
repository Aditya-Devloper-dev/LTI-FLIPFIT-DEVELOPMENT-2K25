package com.lti.flipfit.rest;

import org.springframework.web.bind.annotation.*;

/**
 * Author      :
 * Version     : 1.0
 * Description : Controller for handling all booking operations.
 */
@RestController
@RequestMapping("/booking")
public class BookingFlipFitController {

    @PostMapping("/create")
    public String createBooking(@RequestParam String customerId,
                                @RequestParam String centerId,
                                @RequestParam String slotId) {
        return null;
    }

    @DeleteMapping("/cancel/{bookingId}")
    public boolean cancelBooking(@PathVariable String bookingId) {
        return false;
    }

    @PostMapping("/confirm/{bookingId}")
    public boolean confirmBooking(@PathVariable String bookingId) {
        return false;
    }

    @PostMapping("/request-owner-approval/{bookingId}")
    public boolean requestOwnerApproval(@PathVariable String bookingId) {
        return false;
    }
}
