package com.lti.flipfit.constants;

public class JPAQLConstants {
    public static final String JPQL_TOGGLE_CENTER_STATUS = "UPDATE GymCenter c SET c.isActive = CASE WHEN c.isActive = true THEN false ELSE true END WHERE c.centerId = :centerId AND c.owner.id = :ownerId";
    public static final String JPQL_TOGGLE_SLOT_STATUS = "UPDATE GymSlot s SET s.isActive = CASE WHEN s.isActive = true THEN false ELSE true END WHERE s.slotId = :slotId AND s.center.owner.id = :ownerId";
}
