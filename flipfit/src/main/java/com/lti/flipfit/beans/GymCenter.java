package com.lti.flipfit.beans;

/**
 * Author      :
 * Version     : 1.0
 * Description : Represents a physical gym center with slots and address.
 */
public class GymCenter {

    private String centerId;
    private String centerName;
    private String contactNumber;
    private String city;
    private boolean isActive;
    private double rating;
    private String gymAddressId;

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getGymAddressId() {
        return gymAddressId;
    }

    public void setGymAddressId(String gymAddressId) {
        this.gymAddressId = gymAddressId;
    }

}
