package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JpaUserMealRepositoryImpl implements UserMealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public UserMeal save(UserMeal userMeal, int userId) {
        if (userMeal.isNew()) {
            User userRef = em.getReference(User.class, userId);
            userMeal.setUser(userRef);
            em.persist(userMeal);
            return userMeal;
        } else {
            return get(userMeal.getId(), userId) != null ? em.merge(userMeal) : null;
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(UserMeal.DELETE)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .executeUpdate() != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
/*        UserMeal meal = em.find(UserMeal.class, id);
        return meal.getUser().getId() == userId ? meal : null;*/

        return DataAccessUtils.singleResult(em.createNamedQuery(UserMeal.BY_ID, UserMeal.class)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .getResultList());
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return em.createNamedQuery(UserMeal.ALL_SORTED, UserMeal.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(UserMeal.BETWEEN, UserMeal.class)
                .setParameter("user_id", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}