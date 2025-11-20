package com.lti.flipfit.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "gymnotification")
@Data
public class GymNotification {

    @Id
    @Column(name = "notification_id")
    private String notificationId;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private String message;
    private String type;
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
