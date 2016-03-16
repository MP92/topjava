package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class UserMealServiceImpl implements UserMealService {

    private static final Logger LOG = LoggerFactory.getLogger(UserMealServiceImpl.class);

    @Autowired
    private UserMealRepository repository;

    @Override
    public UserMeal save(int userId, UserMeal userMeal) {
        return repository.save(userId, userMeal);
    }

    @Override
    public void delete(int userId, int mealId) {
        ExceptionUtil.check(repository.delete(userId, mealId), "UserMeal with id=" +
                            mealId + " for user with id=" + userId + " not found");
    }

    @Override
    public UserMeal get(int userId, int mealId) {
        return ExceptionUtil.check(repository.get(userId, mealId), "UserMeal with id=" +
                                    mealId + " for user with id=" + userId + " not found");
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        List<UserMeal> result = repository.getAll(userId);
        return result != null ? result : Collections.emptyList();
    }

    @Override
    public List<UserMeal> getFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        List<UserMeal> result = repository.getFiltered(userId, startDate, endDate);
        return result != null ? result : Collections.emptyList();
    }

    @Override
    public void update(int userId, UserMeal userMeal) {
        ExceptionUtil.check(userMeal.getId(), "Missing userMeal id");
        ExceptionUtil.check(repository.save(userId, userMeal),
                "Can't find meals owner with userId=" + userId);
    }
}
