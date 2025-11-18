package com.lti.flipfit.rest;

import com.lti.flipfit.services.CustomerFlipFitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerFlipFitController {

    private final CustomerFlipFitService customerService;

    public CustomerFlipFitController(CustomerFlipFitService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/availability")
    public List<Map<String, Object>> viewAvailability(
            @RequestParam String centerId,
            @RequestParam String date) {

        return customerService.viewAvailability(centerId, date);
    }

    @PostMapping("/book")
    public String bookSlot(
            @RequestParam String customerId,
            @RequestParam String slotId,
            @RequestParam String centerId) {

        return customerService.bookSlot(customerId, slotId, centerId);
    }

    @DeleteMapping("/cancel/{bookingId}")
    public boolean cancelBooking(@PathVariable String bookingId) {
        return customerService.cancelBooking(bookingId);
    }
}
