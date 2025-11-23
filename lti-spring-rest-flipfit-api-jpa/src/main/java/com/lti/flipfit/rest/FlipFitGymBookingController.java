package com.lti.flipfit.rest;

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.services.FlipFitGymBookingService;
import com.lti.flipfit.exceptions.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Author :
 * Version : 1.0
 * Description : Controller for managing the complete booking lifecycle
 * including
 * booking creation, cancellation, and fetching user-specific bookings.
 */

@RestController
@RequestMapping("/booking")
public class FlipFitGymBookingController {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymBookingController.class);

    private final FlipFitGymBookingService bookingService;

    public FlipFitGymBookingController(FlipFitGymBookingService bookingService) {
        this.bookingService = bookingService;
    }

    /*
     * @Method: bookSlot
     * 
     * @Description: Accepts booking details, validates them, then forwards to
     * service layer
     * 
     * @MethodParameters: Booking booking
     * 
     * @Exception: Throws InvalidBookingException for missing data,
     * and service-level exceptions for conflict/full/slot not found
     */

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public ResponseEntity<String> bookSlot(@RequestBody GymBooking booking) {
        logger.info("Received request to book slot");

        if (booking.getCustomer() == null ||
                booking.getSlot() == null ||
                booking.getCenter() == null) {
            throw new InvalidInputException("customer, slot, center are required");
        }

        return ResponseEntity.ok(bookingService.bookSlot(booking));
    }

    /*
     * @Method: cancelBooking
     * 
     * @Description: Cancels a booking by its ID and updates availability
     * 
     * @MethodParameters: bookingId -> unique booking ID
     * 
     * @Exception: Throws InvalidInputException if bookingId is empty,
     * booking-related exceptions if booking doesn't exist
     */
    @RequestMapping(value = "/cancel/{bookingId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId) {
        logger.info("Received request to cancel booking with ID: {}", bookingId);
        return ResponseEntity.ok(bookingService.cancelBooking(bookingId));
    }

}
