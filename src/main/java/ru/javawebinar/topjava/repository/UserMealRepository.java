package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDate;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public interface UserMealRepository {
    UserMeal save(int userId, UserMeal userMeal);

    boolean delete(int userId, int mealId);

    UserMeal get(int userId, int mealId);

    List<UserMeal> getAll(int userId);

    List<UserMeal> getFiltered(int userId, LocalDate startDate, LocalDate endDate);
}
