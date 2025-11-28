package com.lti.flipfit.dao;

import com.lti.flipfit.entity.User;

/**
 * Author :
 * Version : 1.0
 * Description : Interface for User Data Access Object.
 * Handles database operations for User and related entities using JPQL.
 */
public interface FlipFitGymUserDAO {
    /**
     * Finds a user by their email address.
     * 
     * @param email The email to search for.
     * @return The User object if found, null otherwise.
     */
    User findUserByEmail(String email);

    /**
     * Checks if a user exists with the given email.
     * 
     * @param email The email to check.
     * @return true if exists, false otherwise.
     */
    boolean checkUserExists(String email);

    /**
     * Checks if a user exists with the given email and phone number.
     * 
     * @param email       The email to check.
     * @param phoneNumber The phone number to check.
     * @return true if exists, false otherwise.
     */
    boolean checkUserExistsByEmailAndPhone(String email, String phoneNumber);
}
