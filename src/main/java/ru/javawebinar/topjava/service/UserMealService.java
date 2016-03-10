package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

public interface UserMealService {
    List<UserMeal> findAllUserMeals();

    UserMeal findUserMealById(int id);

    void addUserMeal(UserMeal userMeal);

    void updateUserMeal(UserMeal userMeal);

    boolean deleteUserMealById(int id);
}
