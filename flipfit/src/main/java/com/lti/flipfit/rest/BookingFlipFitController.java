package com.lti.flipfit.rest;

import com.lti.flipfit.beans.Booking;
import com.lti.flipfit.services.BookingFlipFitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author      :
 * Version     : 1.0
 * Description : Controller for handling all booking operations.
 */
@RestController
@RequestMapping("/booking")
public class BookingFlipFitController {

    private BookingFlipFitService bookingService;

    public BookingFlipFitController(BookingFlipFitService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public ResponseEntity<String> bookSlot(@RequestBody Booking booking) {
        return null;
    }

    @DeleteMapping("/cancel/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable String bookingId) {
        return null;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable String userId) {
        return null;
    }
}

