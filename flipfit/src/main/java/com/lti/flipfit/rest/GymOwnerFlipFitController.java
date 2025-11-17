package com.lti.flipfit.rest;

import org.springframework.web.bind.annotation.*;

/**
 * Author      :
 * Version     : 1.0
 * Description : Controller for gym owners to manage bookings and centers.
 */
@RestController
@RequestMapping("/owner")
public class GymOwnerFlipFitController {

    @PostMapping("/approve-booking/{bookingId}")
    public boolean approveBooking(@PathVariable String bookingId) {
        return false;
    }

    @PostMapping("/add-center")
    public boolean addCenter(@RequestParam String ownerId,
                             @RequestParam String centerId) {
        return false;
    }

    @PutMapping("/update-center/{centerId}")
    public boolean updateCenter(@PathVariable String centerId) {
        return false;
    }

    @GetMapping("/all-bookings/{centerId}")
    public Object viewAllBookings(@PathVariable String centerId) {
        return null;
    }
}
