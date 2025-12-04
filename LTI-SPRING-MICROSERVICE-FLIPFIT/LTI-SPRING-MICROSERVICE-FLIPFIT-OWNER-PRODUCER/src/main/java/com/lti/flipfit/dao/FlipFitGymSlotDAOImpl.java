package com.lti.flipfit.dao;

import com.lti.flipfit.constants.JPAQLConstants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * Author :
 * Version : 1.0
 * Description : Implementation of FlipFitGymSlotDAO using EntityManager and
 * JPQL.
 */
@Repository
public class FlipFitGymSlotDAOImpl implements FlipFitGymSlotDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void resetAvailableSeatsForActiveSlots() {
        entityManager.createQuery(JPAQLConstants.JPQL_RESET_SLOT_CAPACITY)
                .executeUpdate();
    }
}
