package com.lti.flipfit.dao;

import com.lti.flipfit.constants.JPAQLConstants;
import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCenter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

/**
 * Author :
 * Version : 1.0
 * Description : Implementation of FlipFitGymCustomerDAO using EntityManager and
 * JPQL.
 */
@Repository
public class FlipFitGymCustomerDAOImpl implements FlipFitGymCustomerDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> findSlotAvailability(Long centerId, LocalDate date) {
        TypedQuery<Object[]> query = entityManager.createQuery(JPAQLConstants.JPQL_FIND_SLOT_AVAILABILITY,
                Object[].class);
        query.setParameter("centerId", centerId);
        query.setParameter("date", date);
        return query.getResultList();
    }

    @Override
    public List<GymBooking> findBookingsByCustomerId(Long customerId) {
        TypedQuery<GymBooking> query = entityManager.createQuery(JPAQLConstants.JPQL_FIND_BOOKINGS_BY_CUSTOMER_ID,
                GymBooking.class);
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }

    @Override
    public List<GymCenter> findActiveGyms() {
        TypedQuery<GymCenter> query = entityManager.createQuery(JPAQLConstants.JPQL_FIND_ACTIVE_GYMS, GymCenter.class);
        return query.getResultList();
    }
}
