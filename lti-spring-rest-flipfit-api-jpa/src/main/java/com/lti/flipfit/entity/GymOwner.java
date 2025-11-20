package com.lti.flipfit.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "gymowner")
@Data
public class GymOwner {

    @Id
    @Column(name = "owner_id")
    private String ownerId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "gst_number")
    private String gstNumber;

    @Column(name = "pan_number")
    private String panNumber;
}
