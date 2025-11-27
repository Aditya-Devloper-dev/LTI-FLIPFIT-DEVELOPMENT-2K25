package com.lti.flipfit.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Author :
 * Version : 1.0
 * Description : Entity class representing Gym Payment.
 */
@Entity
@Table(name = "gympayment")
@Data
public class GymPayment {

    @Id
    @Column(name = "payment_id")
    private String paymentId;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private GymBooking booking;

    private Double amount;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "transaction_id")
    private String transactionId;

    @ManyToOne
    @JoinColumn(name = "payment_mode_id")
    private GymPaymentMode paymentMode;
}
