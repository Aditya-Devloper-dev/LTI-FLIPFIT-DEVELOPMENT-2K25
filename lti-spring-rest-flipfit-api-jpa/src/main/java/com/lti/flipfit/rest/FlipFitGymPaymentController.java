package com.lti.flipfit.rest;

import com.lti.flipfit.services.FlipFitGymPaymentService;
import org.springframework.web.bind.annotation.*;

/**
 * Author      :
 * Version     : 1.0
 * Description : Controller for processing payments and refunds.
 */
@RestController
@RequestMapping("/payment")
public class FlipFitGymPaymentController {

    private final FlipFitGymPaymentService service;

    public FlipFitGymPaymentController(FlipFitGymPaymentService service) {
        this.service = service;
    }

    @PostMapping("/process")
    public boolean processPayment(@RequestParam String bookingId,
                                  @RequestParam double amount) {
        return service.processPayment(bookingId, amount);
    }

    @PostMapping("/refund/{paymentId}")
    public boolean refund(@PathVariable String paymentId) {
        return service.refund(paymentId);
    }

    @GetMapping("/status/{paymentId}")
    public String checkStatus(@PathVariable String paymentId) {
        return service.checkStatus(paymentId);
    }
}
