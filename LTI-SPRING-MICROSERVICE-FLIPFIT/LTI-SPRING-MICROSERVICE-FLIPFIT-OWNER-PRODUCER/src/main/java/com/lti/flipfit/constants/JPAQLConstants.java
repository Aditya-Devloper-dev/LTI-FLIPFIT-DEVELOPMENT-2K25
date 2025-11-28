package com.lti.flipfit.constants;

public class JPAQLConstants {
    public static final String JPQL_FIND_BOOKINGS_BY_CENTER_ID = "SELECT b FROM GymBooking b WHERE b.center.centerId = :centerId";
    public static final String JPQL_FIND_CENTERS_BY_OWNER_ID = "SELECT c FROM GymCenter c WHERE c.owner.ownerId = :ownerId";
}
