package com.lti.flipfit.services;

/**
 * Author      :
 * Version     : 1.0
 * Description : Handles payment processing for bookings.
 */
public interface FlipFitGymPaymentService {

    boolean processPayment(String bookingId, double amount);

    boolean refund(String paymentId);

    String checkStatus(String paymentId);

}
