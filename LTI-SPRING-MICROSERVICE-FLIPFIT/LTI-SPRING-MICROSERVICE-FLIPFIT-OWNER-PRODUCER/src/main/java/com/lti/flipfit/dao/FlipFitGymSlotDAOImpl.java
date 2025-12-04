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

    @Override
    public boolean checkSlotOverlap(Long centerId, java.time.LocalTime startTime, java.time.LocalTime endTime) {
        Long count = entityManager.createQuery(JPAQLConstants.JPQL_CHECK_SLOT_OVERLAP, Long.class)
                .setParameter("centerId", centerId)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime)
                .getSingleResult();
        return count > 0;
    }
}
