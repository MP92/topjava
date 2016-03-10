package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UserMealDaoMapImpl implements UserMealDao {
    private static final AtomicInteger nextId = new AtomicInteger(0);

    private Map<Integer, UserMeal> userMeals = new ConcurrentHashMap<>();
    {
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public UserMeal findById(int id) {
        return userMeals.get(id);
    }

    @Override
    public Collection<UserMeal> findAll() {
        return userMeals.values();
    }

    @Override
    public void save(UserMeal userMeal) {
        if (userMeal.isNew()) {
            userMeal.setId(nextId.getAndIncrement());
        }
        userMeals.put(userMeal.getId(), userMeal);
    }

    @Override
    public boolean deleteById(int id) {
        return userMeals.remove(id) != null;
    }
}
