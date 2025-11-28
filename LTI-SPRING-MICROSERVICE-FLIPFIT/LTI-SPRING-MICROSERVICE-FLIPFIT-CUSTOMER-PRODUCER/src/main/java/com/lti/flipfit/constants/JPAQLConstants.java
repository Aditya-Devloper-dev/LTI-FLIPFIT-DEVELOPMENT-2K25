package com.lti.flipfit.constants;

public class JPAQLConstants {
    public static final String JPQL_FIND_SLOT_AVAILABILITY = "SELECT s, COUNT(b) FROM GymSlot s " +
            "LEFT JOIN GymBooking b ON b.slot = s AND b.bookingDate = :date " +
            "WHERE s.center.centerId = :centerId AND s.isActive = true AND s.isApproved = true AND s.center.isApproved = true AND s.center.isActive = true "
            +
            "GROUP BY s";

    public static final String JPQL_FIND_BOOKINGS_BY_CUSTOMER_ID = "SELECT b FROM GymBooking b WHERE b.customer.customerId = :customerId";

    public static final String JPQL_FIND_ACTIVE_GYMS = "SELECT c FROM GymCenter c WHERE c.isActive = true AND c.isApproved = true";
}
