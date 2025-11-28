package com.lti.flipfit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Author :
 * Version : 1.0
 * Description : Entity class representing Gym Center.
 */
@Entity
@Table(name = "gymcenter")
@Data
public class GymCenter implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = true) // Made nullable as Owner is primary now
    private GymAdmin admin;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private GymOwner owner;

    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<GymSlot> slots = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_active")
    private Boolean isActive = false;

    @Column(name = "is_approved")
    private Boolean isApproved = false;
}
