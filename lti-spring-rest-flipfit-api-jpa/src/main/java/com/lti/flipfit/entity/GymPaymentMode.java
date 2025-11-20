package com.lti.flipfit.entity;

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
