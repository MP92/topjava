package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.dao.UserMealDao;
import ru.javawebinar.topjava.dao.UserMealDaoMapImpl;
import ru.javawebinar.topjava.model.UserMeal;

import java.util.Collection;

public class UserMealServiceImpl implements UserMealService {

    private UserMealDao dao = new UserMealDaoMapImpl();

    @Override
    public Collection<UserMeal> findAllUserMeals() {
        return dao.findAll();
    }

    @Override
    public UserMeal findUserMealById(int id) {
        return dao.findById(id);
    }

    @Override
    public void saveUserMeal(UserMeal userMeal) {
        dao.save(userMeal);
    }

    @Override
    public boolean deleteUserMealById(int id) {
        return dao.deleteById(id);
    }
}
