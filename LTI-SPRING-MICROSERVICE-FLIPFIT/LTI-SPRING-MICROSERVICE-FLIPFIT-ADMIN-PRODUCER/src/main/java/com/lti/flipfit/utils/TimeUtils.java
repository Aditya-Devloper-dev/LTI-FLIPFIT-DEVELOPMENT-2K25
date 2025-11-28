package com.lti.flipfit.utils;

import java.time.LocalTime;

/**
 * Author :
 * Version : 1.0
 * Description : Utility class for time-related operations.
 */
public class TimeUtils {

    /**
     * Helper method to check if two time ranges overlap.
     * 
     * @param s1 Start time of first range.
     * @param e1 End time of first range.
     * @param s2 Start time of second range.
     * @param e2 End time of second range.
     * @return true if they overlap, false otherwise.
     */
    public static boolean timesOverlap(LocalTime s1, LocalTime e1, LocalTime s2, LocalTime e2) {
        return s1.isBefore(e2) && s2.isBefore(e1);
    }
}
