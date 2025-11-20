package com.lti.flipfit.beans;

import java.time.LocalTime;

/**
 * Author      :
 * Version     : 1.0
 * Description : Represents a single workout slot under a center including time,
 *               capacity, seat availability and waitlist settings.
 */
public class Slot {

    private String slotId;
    private String centerId;
    private LocalTime startTime;
    private LocalTime endTime;
    private int capacity;
    private int availableSeats;
    private String status;
    private boolean isWaitlistEnabled;

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
        this.availableSeats = capacity; // initialize available seats
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isWaitlistEnabled() {
        return isWaitlistEnabled;
    }

    public void setWaitlistEnabled(boolean waitlistEnabled) {
        isWaitlistEnabled = waitlistEnabled;
    }
}
