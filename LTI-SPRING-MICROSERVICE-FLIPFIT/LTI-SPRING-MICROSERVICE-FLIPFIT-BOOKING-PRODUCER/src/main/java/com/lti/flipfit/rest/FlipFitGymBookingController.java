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
 * including booking creation, cancellation, and fetching user-specific
 * bookings.
 */

@RestController
@RequestMapping("/booking")
public class FlipFitGymBookingController {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymBookingController.class);

    private final FlipFitGymBookingService bookingService;

    public FlipFitGymBookingController(FlipFitGymBookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * @methodname - bookSlot
     * @description - Accepts booking details, validates them, then forwards to
     *              service layer.
     * @param - booking The booking request payload.
     * @return - A response entity containing the booking status.
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

    /**
     * @methodname - cancelBooking
     * @description - Cancels a booking by its ID and updates availability.
     * @param - bookingId The unique booking ID.
     * @return - A response entity containing the cancellation status.
     */
    @RequestMapping(value = "/cancel/{bookingId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId) {
        logger.info("Received request to cancel booking with ID: {}", bookingId);
        return ResponseEntity.ok(bookingService.cancelBooking(bookingId));
    }

    /**
     * @methodname - viewPayments
     * @description - Retrieves payments based on filter type and date.
     * @param - filterType The type of filter (ALL, MONTHLY, WEEKLY, DAILY).
     * @param - date The specific date for DAILY filter (YYYY-MM-DD).
     * @return - ResponseEntity containing a list of GymPayment objects.
     */
    @RequestMapping(value = "/payments", method = RequestMethod.GET)
    public ResponseEntity<java.util.List<com.lti.flipfit.entity.GymPayment>> viewPayments(
            @RequestParam(defaultValue = "ALL") String filterType,
            @RequestParam(required = false) String date) {
        logger.info("Received request to view payments with filter: {} and date: {}", filterType, date);
        return ResponseEntity.ok(bookingService.viewPayments(filterType, date));
    }

}
