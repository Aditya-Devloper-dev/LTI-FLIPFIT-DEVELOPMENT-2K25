package com.lti.flipfit.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Author :
 * Version : 1.0
 * Description : Feign Client for communicating with the Customer Microservice
 * (Payment Service).
 */
@FeignClient(name = "LTI-SPRING-MICROSERVICE-FLIPFIT-CUSTOMER-PRODUCER")
public interface PaymentClient {

    /**
     * @methodname - processPayment
     * @description - Calls the payment processing endpoint.
     * @param - customerId The ID of the customer
     * @param - amount The amount to pay
     * @param - paymentMode The mode of payment
     * @return - ResponseEntity containing the transaction ID
     */
    @PostMapping("/payment/process")
    ResponseEntity<String> processPayment(
            @RequestParam("customerId") String customerId,
            @RequestParam("amount") Double amount,
            @RequestParam("paymentMode") String paymentMode,
            @RequestParam("bookingId") Long bookingId);

    /**
     * @methodname - refundPayment
     * @description - Calls the payment refund endpoint.
     * @param - paymentId The ID of the payment to refund
     * @return - ResponseEntity with status message
     */
    @PostMapping("/payment/refund")
    ResponseEntity<String> refundPayment(@RequestParam("bookingId") Long bookingId);
}
