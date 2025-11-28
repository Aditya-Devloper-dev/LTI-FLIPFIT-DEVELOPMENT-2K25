package com.lti.flipfit.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

/**
 * Author :
 * Version : 1.0
 * Description : Entity class representing Gym Payment.
 */
@Entity
@Table(name = "gympayment")
@Data
public class GymPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "booking_id")
    private Long bookingId;

    private Double amount;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "transaction_id")
    private String transactionId;

    @ManyToOne
    @JoinColumn(name = "payment_mode_id")
    private GymPaymentMode paymentMode;
}
