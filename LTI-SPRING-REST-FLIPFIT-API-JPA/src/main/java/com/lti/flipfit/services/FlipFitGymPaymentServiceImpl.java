package com.lti.flipfit.services;

/**
 * Author :
 * Version : 1.0
 * Description : Implementation of the FlipFitGymPaymentService interface.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FlipFitGymPaymentServiceImpl implements FlipFitGymPaymentService {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymPaymentServiceImpl.class);

    private final Map<String, String> paymentStore = new HashMap<>();

    @Override
    public boolean processPayment(String bookingId, double amount) {

        String paymentId = UUID.randomUUID().toString();
        paymentStore.put(paymentId, "SUCCESS");

        logger.info("Payment processed. Booking ID: {}, Amount: {}, Payment ID: {}", bookingId, amount, paymentId);

        System.out.println("Dummy payment processed:");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Amount: " + amount);
        System.out.println("Payment ID: " + paymentId);

        return true;
    }

    @Override
    public boolean refund(String paymentId) {
        if (paymentStore.containsKey(paymentId)) {
            paymentStore.put(paymentId, "REFUNDED");
            logger.info("Refund processed for paymentId: {}", paymentId);
            System.out.println("Refund processed for paymentId: " + paymentId);
            return true;
        }
        return false;
    }

    @Override
    public String checkStatus(String paymentId) {
        return paymentStore.getOrDefault(paymentId, "NOT_FOUND");
    }
}
