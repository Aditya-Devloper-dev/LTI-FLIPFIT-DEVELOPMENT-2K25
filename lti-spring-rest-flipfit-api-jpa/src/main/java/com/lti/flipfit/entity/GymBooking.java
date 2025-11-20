package com.lti.flipfit.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "gymbooking")
@Data
public class GymBooking {

    @Id
    @Column(name = "booking_id")
    private String bookingId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private GymCustomer customerId;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private GymCenter centerId;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private GymSlot slotId;

    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "owner_approval_required")
    private Boolean ownerApprovalRequired;

    @Column(name = "approved_by_owner")
    private Boolean approvedByOwner;
}
