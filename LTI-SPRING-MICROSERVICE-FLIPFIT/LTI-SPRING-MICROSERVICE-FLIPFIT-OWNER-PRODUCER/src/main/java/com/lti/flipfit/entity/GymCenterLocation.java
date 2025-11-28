package com.lti.flipfit.entity;

/**
 * Author :
 * Version : 1.0
 * Description : Entity class representing Gym Center Location.
 */

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "gymcenterlocation")
@Data
public class GymCenterLocation implements Serializable {

    private static final long serialVersionUID = 1L;

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
