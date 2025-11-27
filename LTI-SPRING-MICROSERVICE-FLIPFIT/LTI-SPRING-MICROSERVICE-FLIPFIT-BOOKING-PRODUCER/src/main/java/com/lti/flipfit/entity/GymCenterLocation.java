package com.lti.flipfit.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Author :
 * Version : 1.0
 * Description : Entity class representing Gym Center Location.
 */
@Entity
@Table(name = "gymcenterlocation")
@Data
public class GymCenterLocation {

    @Id
    @Column(name = "address_id")
    private String addressId;

    private String street;
    private String locality;
    private String city;
    private String state;
    private String pincode;
    private String landmark;
}
