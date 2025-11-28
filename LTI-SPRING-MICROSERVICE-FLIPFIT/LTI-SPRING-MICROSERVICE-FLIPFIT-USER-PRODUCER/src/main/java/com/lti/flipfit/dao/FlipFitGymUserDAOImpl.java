package com.lti.flipfit.dao;

import com.lti.flipfit.constants.JPAQLConstants;
import com.lti.flipfit.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 * Author :
 * Version : 1.0
 * Description : Implementation of FlipFitGymUserDAO using EntityManager and
 * JPQL.
 */
@Repository
public class FlipFitGymUserDAOImpl implements FlipFitGymUserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findUserByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery(JPAQLConstants.JPQL_FIND_USER_BY_EMAIL, User.class);
        query.setParameter("email", email);
        return query.getResultStream().findFirst().orElse(null);
    }

    @Override
    public boolean checkUserExists(String email) {
        TypedQuery<Long> query = entityManager.createQuery(JPAQLConstants.JPQL_COUNT_USER_BY_EMAIL, Long.class);
        query.setParameter("email", email);
        return query.getSingleResult() > 0;
    }

    @Override
    public boolean checkUserExistsByEmailAndPhone(String email, String phoneNumber) {
        TypedQuery<Long> query = entityManager.createQuery(JPAQLConstants.JPQL_COUNT_USER_BY_EMAIL_AND_PHONE,
                Long.class);
        query.setParameter("email", email);
        query.setParameter("phoneNumber", phoneNumber);
        return query.getSingleResult() > 0;
    }
}
