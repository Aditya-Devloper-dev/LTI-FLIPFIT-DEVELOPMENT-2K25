package com.lti.flipfit.entity;

/**
 * Author :
 * Version : 1.0
 * Description : Entity class representing Gym Slot.
 */

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;

@Entity
@Table(name = "gymslot")
@Data
public class GymSlot {

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
}
