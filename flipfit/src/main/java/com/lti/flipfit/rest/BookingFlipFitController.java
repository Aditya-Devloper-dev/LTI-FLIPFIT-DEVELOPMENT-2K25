package com.lti.flipfit.rest;

import com.lti.flipfit.beans.Booking;
import com.lti.flipfit.services.BookingFlipFitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingFlipFitController {

    private final BookingFlipFitService bookingService;

    public BookingFlipFitController(BookingFlipFitService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public ResponseEntity<String> bookSlot(@RequestBody Booking booking) {
        String response = bookingService.bookSlot(booking);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cancel/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable String bookingId) {
        String response = bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable String userId) {
        List<Booking> bookings = bookingService.getUserBookings(userId);
        return ResponseEntity.ok(bookings);
    }
}
