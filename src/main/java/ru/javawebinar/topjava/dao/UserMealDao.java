package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

public interface UserMealDao {
    UserMeal findUserMealById(int id);

    List<UserMeal> findAllUserMeals();

    void addUserMeal(UserMeal userMeal);

    void updateUserMeal(UserMeal userMeal);

    boolean deleteUserMealById(int id);
}
