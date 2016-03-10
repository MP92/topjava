package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.Collection;

public interface UserMealDao {
    UserMeal findById(int id);

    Collection<UserMeal> findAll();

    void save(UserMeal userMeal);

    boolean deleteById(int id);
}
