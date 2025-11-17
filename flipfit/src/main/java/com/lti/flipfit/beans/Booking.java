package com.lti.flipfit.beans;

/**
 * Author      :
 * Version     : 1.0
 * Description : Represents a booking made by a customer for a slot.
 */
public class Booking {

    private String bookingId;
    private String customerId;
    private String centerId;
    private String slotId;
    private String status;
    private String createdAt;
    private boolean ownerApprovalRequired;
    private boolean approvedByOwner;

}
