package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
public interface UserMealRepository {
    UserMeal save(int userId, UserMeal userMeal);

    boolean delete(int userId, int mealId);

    UserMeal get(int userId, int mealId);

    Collection<UserMeal> getAll(int userId);

    Collection<UserMeal> getFiltered(int userId, LocalDateTime startDT, LocalDateTime endDT);
}
