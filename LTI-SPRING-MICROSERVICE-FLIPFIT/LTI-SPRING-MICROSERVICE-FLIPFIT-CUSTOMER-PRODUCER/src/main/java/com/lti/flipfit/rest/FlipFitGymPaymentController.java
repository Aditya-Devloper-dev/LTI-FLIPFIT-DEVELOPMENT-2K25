package com.lti.flipfit.rest;

import com.lti.flipfit.services.FlipFitGymPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * Author :
 * Version : 1.0
 * Description : Controller for processing payments and refunds.
 */
@RestController
@RequestMapping("/payment")
public class FlipFitGymPaymentController {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymPaymentController.class);

    private final FlipFitGymPaymentService service;

    public FlipFitGymPaymentController(FlipFitGymPaymentService service) {
        this.service = service;
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public boolean processPayment(@RequestParam String bookingId,
            @RequestParam double amount) {
        logger.info("Received request to process payment for booking ID: {}, Amount: {}", bookingId, amount);
        return service.processPayment(bookingId, amount);
    }

    @RequestMapping(value = "/refund/{paymentId}", method = RequestMethod.POST)
    public boolean refund(@PathVariable String paymentId) {
        logger.info("Received request to refund payment ID: {}", paymentId);
        return service.refund(paymentId);
    }

    @RequestMapping(value = "/status/{paymentId}", method = RequestMethod.GET)
    public String checkStatus(@PathVariable String paymentId) {
        logger.info("Received request to check status for payment ID: {}", paymentId);
        return service.checkStatus(paymentId);
    }
}
