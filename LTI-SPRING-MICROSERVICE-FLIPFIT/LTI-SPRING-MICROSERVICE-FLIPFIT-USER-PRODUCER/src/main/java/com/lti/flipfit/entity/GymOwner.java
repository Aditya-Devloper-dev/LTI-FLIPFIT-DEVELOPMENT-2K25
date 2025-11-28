package com.lti.flipfit.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

/**
 * Author :
 * Version : 1.0
 * Description : Entity class representing Gym Owner.
 */
@Entity
@Table(name = "gymowner")
@Data
public class GymOwner implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private Long ownerId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "gst_number")
    private String gstNumber;

    @Column(name = "pan_number")
    private String panNumber;

    @Column(name = "is_approved")
    private boolean isApproved = false;
}
