package com.lti.flipfit.entity;

/**
 * Author :
 * Version : 1.0
 * Description : Entity class representing Gym Payment Mode.
 */

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "gympaymentmode")
@Data
public class GymPaymentMode {

    @Id
    @Column(name = "payment_mode_id")
    private String paymentModeId;

    private String modeName;

    private String description;
}
