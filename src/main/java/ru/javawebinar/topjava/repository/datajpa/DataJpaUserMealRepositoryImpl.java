package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
public class DataJpaUserMealRepositoryImpl implements UserMealRepository {

    @Autowired
    private ProxyUserMealRepository mealProxy;

    @Autowired
    private ProxyUserRepository userProxy;

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        if (!userMeal.isNew() && get(userMeal.getId(), userId) == null) {
            return null;
        }

        User user = userProxy.getOne(userId);
        userMeal.setUser(user);

        return mealProxy.save(userMeal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return mealProxy.delete(id, userId) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        return mealProxy.get(id, userId);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return mealProxy.getAll(userId);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return mealProxy.getBetween(userId, startDate, endDate);
    }
}
