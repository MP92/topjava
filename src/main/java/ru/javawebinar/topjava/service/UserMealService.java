package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

/**
 * GKislin
 * 15.06.2015.
 */
public interface UserMealService {
    UserMeal save(int userId, UserMeal userMeal);

    void delete(int userId, int mealId) throws NotFoundException;

    UserMeal get(int userId, int mealId) throws NotFoundException;

    Collection<UserMeal> getAll(int userId);

    Collection<UserMeal> getFiltered(int userId, LocalDateTime startDT, LocalDateTime endDT);

    void update(int userId, UserMeal userMeal);
}
