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

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isOwnerApprovalRequired() {
        return ownerApprovalRequired;
    }

    public void setOwnerApprovalRequired(boolean ownerApprovalRequired) {
        this.ownerApprovalRequired = ownerApprovalRequired;
    }

    public boolean isApprovedByOwner() {
        return approvedByOwner;
    }

    public void setApprovedByOwner(boolean approvedByOwner) {
        this.approvedByOwner = approvedByOwner;
    }
}
