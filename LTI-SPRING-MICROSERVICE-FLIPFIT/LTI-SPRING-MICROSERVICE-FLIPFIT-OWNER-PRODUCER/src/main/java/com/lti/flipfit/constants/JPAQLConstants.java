package com.lti.flipfit.constants;

public class JPAQLConstants {
    public static final String JPQL_TOGGLE_CENTER_STATUS = "UPDATE GymCenter c SET c.isActive = CASE WHEN c.isActive = true THEN false ELSE true END WHERE c.centerId = :centerId AND c.owner.id = :ownerId";
    public static final String JPQL_TOGGLE_SLOT_STATUS = "UPDATE GymSlot s SET s.isActive = CASE WHEN s.isActive = true THEN false ELSE true END WHERE s.slotId = :slotId AND s.center.centerId IN (SELECT c.centerId FROM GymCenter c WHERE c.owner.id = :ownerId)";
    public static final String JPQL_RESET_SLOT_CAPACITY = "UPDATE GymSlot s SET s.availableSeats = s.capacity WHERE s.isActive = true";
    public static final String JPQL_CHECK_SLOT_OVERLAP = "SELECT COUNT(s) FROM GymSlot s WHERE s.center.centerId = :centerId AND s.startTime < :endTime AND s.endTime > :startTime";
}
