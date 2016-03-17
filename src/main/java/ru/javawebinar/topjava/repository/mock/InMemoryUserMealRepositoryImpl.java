package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public static final Comparator<UserMeal> USER_MEAL_COMPARATOR = Comparator.comparing(UserMeal::getDateTime).reversed();

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
        } else if (get(userId, userMeal.getId()) == null) {
            return null;
        }
        return repository.computeIfAbsent(userId, ConcurrentHashMap::new).put(userMeal.getId(), userMeal);
    }

    @Override
    public boolean delete(int userId, int mealId) {
        LOG.info("delete userId={}, mealId={}", userId, mealId);

        Map<Integer, UserMeal> meals = repository.get(userId);

        return meals != null && meals.remove(mealId) != null;
    }

    @Override
    public void deleteAll(int userId) {
        LOG.info("deleteAll userId={}", userId);

        repository.getOrDefault(userId, Collections.emptyMap()).clear();
    }

    @Override
    public UserMeal get(int userId, int mealId) {
        LOG.info("get userId={}, mealId={}", userId, mealId);

        Map<Integer, UserMeal> meals = repository.get(userId);

        return meals != null ? meals.get(mealId) : null;
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        LOG.info("getAll userId={}", userId);

        Map<Integer, UserMeal> meals = repository.get(userId);

        return meals == null ?
                Collections.emptyList() :
                meals.values().stream().sorted(USER_MEAL_COMPARATOR).collect(Collectors.toList());
    }

    @Override
    public List<UserMeal> getBetween(int userId, LocalDateTime startDT, LocalDateTime endDT) {
        LOG.info("getBetween userId={}, startDateTime={}, endDateTime={}", userId, startDT, endDT);

        Map<Integer, UserMeal> meals = repository.get(userId);

        return meals == null ?
                Collections.emptyList() :
                meals.values().stream()
                    .filter(um -> DateTimeUtil.isBetween(um.getDateTime(), startDT, endDT))
                    .sorted(USER_MEAL_COMPARATOR)
                    .collect(Collectors.toList());
    }
}