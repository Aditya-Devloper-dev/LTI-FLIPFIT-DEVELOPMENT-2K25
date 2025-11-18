package com.lti.flipfit.rest;

import com.lti.flipfit.services.GymOwnerFlipFitService;
import org.springframework.web.bind.annotation.*;

/**
 * Author      :
 * Version     : 1.0
 * Description : Controller for gym owners to manage bookings and centers.
 */
@RestController
@RequestMapping("/owner")
public class GymOwnerFlipFitController {

    private final GymOwnerFlipFitService service;

    public GymOwnerFlipFitController(GymOwnerFlipFitService service) {
        this.service = service;
    }

    @PostMapping("/approve-booking/{bookingId}")
    public boolean approveBooking(@PathVariable String bookingId) {
        return service.approveBooking(bookingId);
    }

    @PostMapping("/add-center")
    public boolean addCenter(@RequestParam String ownerId,
                             @RequestParam String centerId) {
        return service.addCenter(ownerId, centerId);
    }

    @PutMapping("/update-center/{centerId}")
    public boolean updateCenter(@PathVariable String centerId) {
        return service.updateCenter(centerId);
    }

    @GetMapping("/all-bookings/{centerId}")
    public Object viewAllBookings(@PathVariable String centerId) {
        return service.viewAllBookings(centerId);
    }
}
