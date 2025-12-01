package com.lti.flipfit.dao;

import com.lti.flipfit.constants.JPAQLConstants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * Author :
 * Version : 1.0
 * Description : Implementation of FlipFitGymOwnerDAO using EntityManager and
 * JPQL.
 */
@Repository
public class FlipFitGymOwnerDAOImpl implements FlipFitGymOwnerDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean toggleCenterStatus(Long centerId, Long ownerId) {
        int updatedCount = entityManager.createQuery(JPAQLConstants.JPQL_TOGGLE_CENTER_STATUS)
                .setParameter("centerId", centerId)
                .setParameter("ownerId", ownerId)
                .executeUpdate();
        return updatedCount > 0;
    }

    @Override
    public boolean toggleSlotStatus(Long slotId, Long ownerId) {
        int updatedCount = entityManager.createQuery(JPAQLConstants.JPQL_TOGGLE_SLOT_STATUS)
                .setParameter("slotId", slotId)
                .setParameter("ownerId", ownerId)
                .executeUpdate();
        return updatedCount > 0;
    }
}
