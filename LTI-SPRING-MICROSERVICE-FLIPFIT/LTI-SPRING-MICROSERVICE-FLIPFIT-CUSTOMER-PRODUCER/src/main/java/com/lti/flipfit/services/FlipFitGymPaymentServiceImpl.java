package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymPayment;
import com.lti.flipfit.entity.GymPaymentMode;
import com.lti.flipfit.repository.FlipFitGymPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import com.lti.flipfit.constants.PaymentModeType;

/**
 * Author :
 * Version : 1.0
 * Description : Implementation of Payment Service with real persistence.
 */
@Service
public class FlipFitGymPaymentServiceImpl implements FlipFitGymPaymentService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
            .getLogger(FlipFitGymPaymentServiceImpl.class);

    @Autowired
    private FlipFitGymPaymentRepository paymentRepository;

    @Autowired
    private com.lti.flipfit.repository.FlipFitGymPaymentModeRepository paymentModeRepository;

    /**
     * @methodname - processPayment
     * @description - Processes a payment by validating inputs and saving to the
     *              database.
     * @param - customerId The ID of the customer making the payment
     * @param - amount The amount to be paid
     * @param - paymentMode The mode of payment
     * @param - bookingId The ID of the booking
     * @return - The transaction ID if successful
     */
    @Override
    @Transactional
    public String processPayment(String customerId, Double amount, String paymentMode, Long bookingId) {
        logger.info("Processing payment for bookingId: {} (Decoupled)", bookingId);
        // In a real scenario, we would validate the customerId against the User Service
        // here.
        // For now, we assume the Booking Service has already validated the user exists.
        // Validate Payment Mode (Simple check for now, can be expanded to check DB)
        if (paymentMode == null || paymentMode.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid payment mode");
        }

        GymPayment payment = new GymPayment();
        payment.setPaymentId(UUID.randomUUID().toString());
        payment.setAmount(amount);
        payment.setPaymentStatus("SUCCESS"); // Assuming immediate success for this demo
        payment.setTransactionId(UUID.randomUUID().toString());

        // Set Booking ID directly
        payment.setBookingId(bookingId);

        // We need to set the PaymentMode entity.
        // Ideally, we fetch it from a repository. For this implementation, we will
        // create a dummy one
        // or assume the ID is passed if we change the signature.
        // Given the current requirement, we'll just set the ID if possible or handle it
        // simply.
        // Let's assume we just store the mode name or ID if the entity allows,
        // but GymPayment has a GymPaymentMode object.
        // To keep it simple and robust without fetching extra entities right now:
        // Validate Payment Mode against Enum
        try {
            PaymentModeType.valueOf(paymentMode.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid payment mode: " + paymentMode + ". Allowed modes are CARD, UPI, NETBANKING.");
        }

        GymPaymentMode mode = paymentModeRepository.findByModeName(paymentMode)
                .orElseGet(() -> {
                    GymPaymentMode newMode = new GymPaymentMode();
                    newMode.setPaymentModeId(UUID.randomUUID().toString());
                    newMode.setModeName(paymentMode);
                    newMode.setDescription("Auto-created mode: " + paymentMode);
                    return paymentModeRepository.save(newMode);
                });
        payment.setPaymentMode(mode);

        paymentRepository.save(payment);

        return payment.getTransactionId();
    }

    /**
     * @methodname - refundPayment
     * @description - Refunds a payment by updating its status to REFUNDED.
     * @param - paymentId The ID of the payment to refund
     * @return - true if refund was successful, false otherwise
     */
    @Override
    @Transactional
    public boolean refundPayment(Long bookingId) {
        GymPayment payment = paymentRepository.findByBookingId(bookingId).orElse(null);
        if (payment != null) {
            payment.setPaymentStatus("REFUNDED");
            paymentRepository.save(payment);
            return true;
        }
        return false;
    }
}
