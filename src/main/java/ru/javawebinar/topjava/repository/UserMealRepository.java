package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public interface UserMealRepository {
    UserMeal save(int userId, UserMeal userMeal);

    boolean delete(int userId, int mealId);

    void deleteAll(int userId);

    UserMeal get(int userId, int mealId);

    List<UserMeal> getAll(int userId);

    List<UserMeal> getBetween(int userId, LocalDateTime startDT, LocalDateTime endDT);
}
