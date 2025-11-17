package com.lti.flipfit.rest;

import org.springframework.web.bind.annotation.*;

/**
 * Author      :
 * Version     : 1.0
 * Description : Controller for processing payments and refunds.
 */
@RestController
@RequestMapping("/payment")
public class PaymentFlipFitController {

    @PostMapping("/process")
    public boolean processPayment(@RequestParam String bookingId,
                                  @RequestParam double amount) {
        return false;
    }

    @PostMapping("/refund/{paymentId}")
    public boolean refund(@PathVariable String paymentId) {
        return false;
    }

    @GetMapping("/status/{paymentId}")
    public String checkStatus(@PathVariable String paymentId) {
        return null;
    }
}
