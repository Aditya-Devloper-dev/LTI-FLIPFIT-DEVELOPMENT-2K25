package com.lti.flipfit.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Author :
 * Version : 1.0
 * Description : Entity class representing Gym Customer.
 */
@Entity
@Table(name = "gymcustomer")
@Data
public class GymCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
