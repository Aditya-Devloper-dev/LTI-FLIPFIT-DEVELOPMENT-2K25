package com.lti.flipfit.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;

@Entity
@Table(name = "gymslot")
@Data
public class GymSlot {

    @Id
    @Column(name = "slot_id")
    private String slotId;

    @ManyToOne
    @JoinColumn(name = "center_id", nullable = false)
    private GymCenter center;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    private Integer capacity;

    @Column(name = "available_seats")
    private Integer availableSeats;

    private String status;

    @Column(name = "is_waitlist_enabled")
    private Boolean waitlistEnabled;
}
