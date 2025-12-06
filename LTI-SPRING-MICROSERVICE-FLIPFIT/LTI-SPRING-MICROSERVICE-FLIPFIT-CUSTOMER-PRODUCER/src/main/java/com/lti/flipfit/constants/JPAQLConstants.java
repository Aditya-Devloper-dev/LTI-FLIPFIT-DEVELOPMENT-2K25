package com.lti.flipfit.constants;

public class JPAQLConstants {
    public static final String JPQL_FIND_ALL_ACTIVE_SLOTS = "SELECT s FROM GymSlot s WHERE s.isActive = true AND s.isApproved = true";

    public static final String JPQL_FIND_BOOKINGS_BY_CUSTOMER_ID = "SELECT b FROM GymBooking b WHERE b.customer.customerId = :customerId";

    public static final String JPQL_FIND_ACTIVE_GYMS = "SELECT c FROM GymCenter c WHERE c.isActive = true AND c.isApproved = true";
}
