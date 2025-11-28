package com.lti.flipfit.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Author :
 * Version : 1.0
 * Description : Entity class representing Gym Booking.
 */
@Entity
@Table(name = "gymbooking")
@Data
public class GymBooking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private GymCustomer customer;

    @ManyToOne
    @JoinColumn(name = "slot_id", nullable = false)
    private GymSlot slot;

    @ManyToOne
    @JoinColumn(name = "center_id", nullable = false)
    private GymCenter center;

    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "owner_approval_required")
    private Boolean ownerApprovalRequired = false;

    @Column(name = "approved_by_owner")
    private Boolean approvedByOwner = false;

    @Column(name = "booking_date")
    private java.time.LocalDate bookingDate;
}
