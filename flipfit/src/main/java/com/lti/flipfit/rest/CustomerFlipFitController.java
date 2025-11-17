package com.lti.flipfit.rest;

import org.springframework.web.bind.annotation.*;

/**
 * Author      :
 * Version     : 1.0
 * Description : Controller for customer slot search, booking, and cancellations.
 */
@RestController
@RequestMapping("/customer")
public class CustomerFlipFitController {

    @GetMapping("/availability")
    public Object viewAvailability(@RequestParam String centerId,
                                   @RequestParam String date) {
        return null;
    }

    @PostMapping("/book")
    public String bookSlot(@RequestParam String customerId,
                           @RequestParam String slotId,
                           @RequestParam String centerId) {
        return null;
    }

    @DeleteMapping("/cancel/{bookingId}")
    public boolean cancelBooking(@PathVariable String bookingId) {
        return false;
    }
}
