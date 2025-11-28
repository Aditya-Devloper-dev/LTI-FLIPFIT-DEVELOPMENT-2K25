package com.lti.flipfit.dao;

import com.lti.flipfit.constants.JPAQLConstants;
import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymOwner;
import com.lti.flipfit.entity.GymSlot;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Author :
 * Version : 1.0
 * Description : Implementation of FlipFitGymAdminDAO using EntityManager and
 * JPQL.
 */
@Repository
public class FlipFitGymAdminDAOImpl implements FlipFitGymAdminDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GymOwner> findPendingOwners() {
        TypedQuery<GymOwner> query = entityManager.createQuery(JPAQLConstants.JPQL_FIND_PENDING_OWNERS, GymOwner.class);
        return query.getResultList();
    }

    @Override
    public List<GymCenter> findPendingCenters() {
        TypedQuery<GymCenter> query = entityManager.createQuery(JPAQLConstants.JPQL_FIND_PENDING_CENTERS,
                GymCenter.class);
        return query.getResultList();
    }

    @Override
    public List<GymSlot> findPendingSlots(Long centerId) {
        TypedQuery<GymSlot> query = entityManager.createQuery(JPAQLConstants.JPQL_FIND_PENDING_SLOTS_BY_CENTER,
                GymSlot.class);
        query.setParameter("centerId", centerId);
        return query.getResultList();
    }

    @Override
    public int approveOwner(Long ownerId) {
        Query query = entityManager.createQuery(JPAQLConstants.JPQL_APPROVE_OWNER_BY_ID);
        query.setParameter("ownerId", ownerId);
        return query.executeUpdate();
    }

    @Override
    public int approveCenter(Long centerId) {
        Query query = entityManager.createQuery(JPAQLConstants.JPQL_APPROVE_CENTER_BY_ID);
        query.setParameter("centerId", centerId);
        return query.executeUpdate();
    }

    @Override
    public int approveSlot(Long slotId) {
        Query query = entityManager.createQuery(JPAQLConstants.JPQL_APPROVE_SLOT_BY_ID);
        query.setParameter("slotId", slotId);
        return query.executeUpdate();
    }
}
