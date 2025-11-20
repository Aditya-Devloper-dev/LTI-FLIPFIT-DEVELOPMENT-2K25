package com.lti.flipfit.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "gymscheduler")
@Data
public class GymScheduler {

    @Id
    @Column(name = "task_id")
    private String taskId;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "run_frequency")
    private String runFrequency;

    @Column(name = "last_run_time")
    private LocalDateTime lastRunTime;
}
