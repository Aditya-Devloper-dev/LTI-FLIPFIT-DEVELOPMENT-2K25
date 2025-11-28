package com.lti.flipfit.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalTime;

/**
 * Author :
 * Version : 1.0
 * Description : Entity class representing Gym Slot.
 */
@Entity
@Table(name = "gymslot")
@Data
public class GymSlot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slot_id")
    private Long slotId;

    @ManyToOne
    @JoinColumn(name = "center_id", nullable = false)
    private GymCenter center;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private int capacity;

    @Column(name = "available_seats")
    private int availableSeats;

    private String status;
    @Column(name = "is_active")
    private Boolean isActive = false;

    @Column(name = "is_approved")
    private Boolean isApproved = false;

    @Column(name = "price")
    private Double price;
}
