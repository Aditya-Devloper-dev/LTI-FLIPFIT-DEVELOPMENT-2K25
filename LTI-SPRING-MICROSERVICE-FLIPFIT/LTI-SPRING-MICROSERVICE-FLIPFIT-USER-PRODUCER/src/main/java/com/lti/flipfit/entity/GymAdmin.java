package com.lti.flipfit.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

/**
 * Author :
 * Version : 1.0
 * Description : Entity class representing Gym Admin.
 */
@Entity
@Table(name = "gymadmin")
@Data
public class GymAdmin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
