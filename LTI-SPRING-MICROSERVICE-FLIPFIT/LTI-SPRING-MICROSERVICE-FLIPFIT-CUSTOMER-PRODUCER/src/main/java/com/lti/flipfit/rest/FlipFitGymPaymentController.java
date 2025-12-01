package com.lti.flipfit.rest;

import com.lti.flipfit.services.FlipFitGymPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Author :
 * Version : 1.0
 * Description : Controller for handling payment-related operations.
 */
@RestController
@RequestMapping("/payment")
public class FlipFitGymPaymentController {

    @Autowired
    private FlipFitGymPaymentService paymentService;

    /**
     * @methodname - processPayment
     * @description - Endpoint to process a payment.
     * @param - customerId The ID of the customer
     * @param - amount The amount to pay
     * @param - paymentMode The mode of payment
     * @return - ResponseEntity containing the transaction ID
     */
    @PostMapping("/process")
    public ResponseEntity<String> processPayment(
            @RequestParam String customerId,
            @RequestParam Double amount,
            @RequestParam String paymentMode,
            @RequestParam Long bookingId) {
        try {
            String transactionId = paymentService.processPayment(customerId, amount, paymentMode, bookingId);
            return new ResponseEntity<>(transactionId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Payment Failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @methodname - refundPayment
     * @description - Endpoint to refund a payment.
     * @param - paymentId The ID of the payment to refund
     * @return - ResponseEntity with status message
     */
    @PostMapping("/refund")
    public ResponseEntity<String> refundPayment(@RequestParam Long bookingId) {
        boolean success = paymentService.refundPayment(bookingId);
        if (success) {
            return new ResponseEntity<>("Refund Processed Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Refund Failed: Payment not found", HttpStatus.NOT_FOUND);
        }
    }
}
