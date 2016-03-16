package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {

    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserMealRepositoryImpl.class);

    private Map<Integer, Map<Integer, UserMeal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        List<UserMeal> meals = UserMealsUtil.MEAL_LIST;

        for (int i = 0; i < meals.size(); i++) {
            save(i%2, meals.get(i));
        }
    }

    @Override
    public UserMeal save(int userId, UserMeal userMeal) {
        LOG.info("save userId={}, {}", userId, userMeal);

        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        } else if (!repository.containsKey(userId)) {
            return null;
        }
        return repository.computeIfAbsent(userId, ConcurrentHashMap::new).put(userMeal.getId(), userMeal);
    }

    @Override
    public boolean delete(int userId, int mealId) {
        LOG.info("delete userId={}, mealId={}", userId, mealId);

        return repository.getOrDefault(userId, Collections.emptyMap()).remove(mealId) != null;
    }

    @Override
    public UserMeal get(int userId, int mealId) {
        LOG.info("get userId={}, mealId={}", userId, mealId);

        return repository.getOrDefault(userId, Collections.emptyMap()).get(mealId);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        LOG.info("getAll userId={}", userId);

        if (repository.containsKey(userId)) {
            return repository.get(userId).values().stream()
                    .sorted(Comparator.comparing(UserMeal::getDateTime).reversed())
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<UserMeal> getFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        LOG.info("getFiltered userId={}, startDate={}, endDate={}", userId, startDate, endDate);

        if (repository.containsKey(userId)) {
            return repository.get(userId).values().stream()
                    .filter(um -> DateTimeUtil.isBetween(um.getDateTime().toLocalDate(), startDate, endDate))
                    .sorted(Comparator.comparing(UserMeal::getDateTime).reversed())
                    .collect(Collectors.toList());
        }
        return null;
    }
}