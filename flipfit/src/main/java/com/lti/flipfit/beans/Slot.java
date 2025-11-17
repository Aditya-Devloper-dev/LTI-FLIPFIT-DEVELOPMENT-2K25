package com.lti.flipfit.beans;

/**
 * Author      :
 * Version     : 1.0
 * Description : Represents a time-slot in a gym center.
 */
public class Slot {

    private String slotId;
    private String centerId;
    private String startTime;
    private String endTime;
    private int capacity;
    private int availableSeats;
    private String status;
    private boolean isWaitlistEnabled;

}
