package com.lti.flipfit.entity;

/**
 * Author :
 * Version : 1.0
 * Description : Entity class representing Gym Waitlist.
 */

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "gymwaitlist")
@Data
public class GymWaitlist {

    @Id
    @Column(name = "waitlist_id")
    private String waitlistId;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private GymSlot slot;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private GymCustomer customer;

    private Integer position;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
