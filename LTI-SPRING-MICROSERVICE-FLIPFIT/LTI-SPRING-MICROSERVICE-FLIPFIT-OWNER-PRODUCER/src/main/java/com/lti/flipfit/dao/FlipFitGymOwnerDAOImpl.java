package com.lti.flipfit.dao;

import com.lti.flipfit.constants.JPAQLConstants;
import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCenter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import java.util.List;

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
    public List<GymBooking> findBookingsByCenterId(Long centerId) {
        TypedQuery<GymBooking> query = entityManager.createQuery(JPAQLConstants.JPQL_FIND_BOOKINGS_BY_CENTER_ID,
                GymBooking.class);
        query.setParameter("centerId", centerId);
        return query.getResultList();
    }

    @Override
    public List<GymCenter> findCentersByOwnerId(Long ownerId) {
        TypedQuery<GymCenter> query = entityManager.createQuery(JPAQLConstants.JPQL_FIND_CENTERS_BY_OWNER_ID,
                GymCenter.class);
        query.setParameter("ownerId", ownerId);
        return query.getResultList();
    }
}
