package com.lti.flipfit.constants;

/**
 * Author :
 * Version : 1.0
 * Description : Constants for JPQL queries used in the DAO layer.
 */
public class JPAQLConstants {
    public static final String JPQL_FIND_USER_BY_EMAIL = "SELECT u FROM User u WHERE u.email = :email";
    public static final String JPQL_COUNT_USER_BY_EMAIL = "SELECT COUNT(u) FROM User u WHERE u.email = :email";
    public static final String JPQL_COUNT_USER_BY_EMAIL_AND_PHONE = "SELECT COUNT(u) FROM User u WHERE u.email = :email AND u.phoneNumber = :phoneNumber";
    public static final String JPQL_FIND_ALL_USERS = "SELECT u FROM User u";
}
