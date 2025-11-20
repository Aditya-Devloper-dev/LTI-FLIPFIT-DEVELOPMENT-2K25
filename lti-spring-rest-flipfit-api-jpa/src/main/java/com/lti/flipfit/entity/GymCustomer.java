package com.lti.flipfit.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "gymcustomer")
@Data
public class GymCustomer {

    @Id
    @Column(name = "customer_id")
    private String customerId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "membership_status")
    private String membershipStatus;
}
