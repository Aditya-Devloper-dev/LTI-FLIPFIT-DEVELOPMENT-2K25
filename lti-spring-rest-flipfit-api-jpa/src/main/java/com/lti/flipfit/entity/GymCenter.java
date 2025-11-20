package com.lti.flipfit.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "gymcenter")
@Data
public class GymCenter {

    @Id
    @Column(name = "center_id")
    private String centerId;

    @Column(name = "center_name")
    private String centerName;

    @Column(name = "contact_number")
    private String contactNumber;

    private String city;

    @Column(name = "is_active")
    private Boolean isActive;

    private Double rating;

    @OneToOne
    @JoinColumn(name = "gym_address_id")
    private GymCenterLocation centerLocation;
}
