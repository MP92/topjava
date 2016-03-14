package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {

    private Map<Integer, Map<Integer, UserMeal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        List<UserMeal> meals = UserMealsUtil.MEAL_LIST;

        for (int i = 0; i < meals.size(); i++) {
            save(1 + i%2, meals.get(i));
        }
    }

    @Override
    public UserMeal save(int userId, UserMeal userMeal) {
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        }

        Map<Integer, UserMeal> rep = repository.containsKey(userId) ? repository.get(userId)
                                                                : new ConcurrentHashMap<>();
        rep.put(userMeal.getId(), userMeal);

        repository.put(userId, rep);

        return userMeal;
    }

    @Override
    public boolean delete(int userId, int mealId) {
        return repository.containsKey(userId) && repository.get(userId).remove(mealId) != null;
    }

    @Override
    public UserMeal get(int userId, int mealId) {
        if (repository.containsKey(userId)) {
            return repository.get(userId).get(mealId);
        }
        return null;
    }

    @Override
    public Collection<UserMeal> getAll(int userId) {

        if (repository.containsKey(userId)) {
            return repository.get(userId).values().stream()
                    .sorted((m1, m2) -> m1.getDateTime().compareTo(m2.getDateTime()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Collection<UserMeal> getFiltered(int userId, LocalDateTime startDT, LocalDateTime endDT) {
        Collection<UserMeal> meals = getAll(userId);
        if (meals != null) {
            return UserMealsUtil.getFiltered(meals, startDT, endDT);
        }
        return null;
    }
}