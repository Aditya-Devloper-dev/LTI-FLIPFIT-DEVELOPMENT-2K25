package com.lti.flipfit.rest;

import com.lti.flipfit.services.FlipFitGymPaymentService;
import org.springframework.web.bind.annotation.*;

/**
 * Author :
 * Version : 1.0
 * Description : Controller for processing payments and refunds.
 */
@RestController
@RequestMapping("/payment")
public class FlipFitGymPaymentController {

    private final FlipFitGymPaymentService service;

    public FlipFitGymPaymentController(FlipFitGymPaymentService service) {
        this.service = service;
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public boolean processPayment(@RequestParam String bookingId,
            @RequestParam double amount) {
        return service.processPayment(bookingId, amount);
    }

    @RequestMapping(value = "/refund/{paymentId}", method = RequestMethod.POST)
    public boolean refund(@PathVariable String paymentId) {
        return service.refund(paymentId);
    }

    @RequestMapping(value = "/status/{paymentId}", method = RequestMethod.GET)
    public String checkStatus(@PathVariable String paymentId) {
        return service.checkStatus(paymentId);
    }
}
