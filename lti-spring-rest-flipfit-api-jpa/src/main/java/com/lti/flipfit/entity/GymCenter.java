package com.lti.flipfit.entity;

/**
 * Author :
 * Version : 1.0
 * Description : Entity class representing Gym Center.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gymcenter")
@Data
public class GymCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "center_id")
    private Long centerId;

    @Column(name = "center_name", nullable = false)
    private String centerName;

    @Column(nullable = false)
    private String city;

    @Column(name = "contact_number")
    private String contactNumber;

    @OneToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private GymAdmin admin;

    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<GymSlot> slots = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
