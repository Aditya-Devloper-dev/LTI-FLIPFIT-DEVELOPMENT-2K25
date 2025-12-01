package com.lti.flipfit.services;

/**
 * Author :
 * Version : 1.0
 * Description : Interface for Payment Service.
 */
public interface FlipFitGymPaymentService {

    /**
     * @methodname - processPayment
     * @description - Processes a payment.
     * @param - customerId The ID of the customer
     * @param - amount The amount to pay
     * @param - paymentMode The mode of payment
     * @return - The transaction ID
     */
    String processPayment(String customerId, Double amount, String paymentMode, Long bookingId);

    /**
     * @methodname - refundPayment
     * @description - Refunds a payment.
     * @param - paymentId The ID of the payment
     * @return - true if successful
     */
    boolean refundPayment(Long bookingId);
}
