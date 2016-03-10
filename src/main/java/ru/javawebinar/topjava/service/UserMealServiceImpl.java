package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.dao.UserMealDao;
import ru.javawebinar.topjava.dao.UserMealDaoMapImpl;
import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

public class UserMealServiceImpl implements UserMealService {

    private UserMealDao dao = new UserMealDaoMapImpl();

    @Override
    public List<UserMeal> findAllUserMeals() {
        return dao.findAllUserMeals();
    }

    @Override
    public UserMeal findUserMealById(int id) {
        return dao.findUserMealById(id);
    }

    @Override
    public void addUserMeal(UserMeal userMeal) {
        dao.addUserMeal(userMeal);
    }

    @Override
    public void updateUserMeal(UserMeal userMeal) {
        dao.updateUserMeal(userMeal);
    }

    @Override
    public boolean deleteUserMealById(int id) {
        return dao.deleteUserMealById(id);
    }
}
