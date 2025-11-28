package com.lti.flipfit.constants;

public class JPAQLConstants {
    public static final String JPQL_CHECK_DUPLICATE_BOOKING = "SELECT COUNT(b) FROM GymBooking b WHERE b.customer.customerId = :customerId AND b.slot.slotId = :slotId";

    public static final String JPQL_FIND_PAYMENTS_BY_DATE_RANGE = "SELECT p FROM GymPayment p WHERE p.booking.createdAt BETWEEN :startDate AND :endDate";
}
