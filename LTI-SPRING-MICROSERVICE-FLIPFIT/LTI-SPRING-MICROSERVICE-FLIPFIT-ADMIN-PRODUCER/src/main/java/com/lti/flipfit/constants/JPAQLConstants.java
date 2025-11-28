package com.lti.flipfit.constants;

public class JPAQLConstants {
    public static final String JPQL_FIND_PENDING_OWNERS = "SELECT o FROM GymOwner o WHERE o.isApproved = false";
    public static final String JPQL_FIND_PENDING_CENTERS = "SELECT c FROM GymCenter c WHERE c.isApproved = false";
    public static final String JPQL_FIND_PENDING_SLOTS_BY_CENTER = "SELECT s FROM GymSlot s WHERE s.center.centerId = :centerId AND s.isApproved = false";

    public static final String JPQL_APPROVE_OWNER_BY_ID = "UPDATE GymOwner o SET o.isApproved = true WHERE o.ownerId = :ownerId";
    public static final String JPQL_APPROVE_CENTER_BY_ID = "UPDATE GymCenter c SET c.isApproved = true WHERE c.centerId = :centerId";
    public static final String JPQL_APPROVE_SLOT_BY_ID = "UPDATE GymSlot s SET s.isApproved = true, s.status = 'AVAILABLE' WHERE s.slotId = :slotId";
}
