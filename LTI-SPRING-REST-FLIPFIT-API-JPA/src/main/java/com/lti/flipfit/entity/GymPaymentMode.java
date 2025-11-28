package com.lti.flipfit.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

/**
 * Author :
 * Version : 1.0
 * Description : Entity class representing Gym Payment Mode.
 */
@Entity
@Table(name = "gympaymentmode")
@Data
public class GymPaymentMode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "payment_mode_id")
    private String paymentModeId;

    private String modeName;

    private String description;
}
