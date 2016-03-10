package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.Collection;

public interface UserMealService {
    Collection<UserMeal> findAllUserMeals();

    UserMeal findUserMealById(int id);

    void saveUserMeal(UserMeal userMeal);

    boolean deleteUserMealById(int id);
}
