package com.lti.flipfit.dao;

import com.lti.flipfit.constants.JPAQLConstants;
import com.lti.flipfit.entity.GymPayment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Author :
 * Version : 1.0
 * Description : Implementation of FlipFitGymBookingDAO using EntityManager and
 * JPQL.
 */
@Repository
public class FlipFitGymBookingDAOImpl implements FlipFitGymBookingDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean checkDuplicateBooking(Long customerId, Long slotId) {
        TypedQuery<Long> query = entityManager.createQuery(JPAQLConstants.JPQL_CHECK_DUPLICATE_BOOKING, Long.class);
        query.setParameter("customerId", customerId);
        query.setParameter("slotId", slotId);
        Long count = query.getSingleResult();
        return count > 0;
    }

    @Override
    public List<GymPayment> findPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        TypedQuery<GymPayment> query = entityManager.createQuery(JPAQLConstants.JPQL_FIND_PAYMENTS_BY_DATE_RANGE,
                GymPayment.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }
}
