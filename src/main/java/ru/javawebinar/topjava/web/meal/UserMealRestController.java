package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {

    private static final Logger LOG = LoggerFactory.getLogger(UserMealRestController.class);

    @Autowired
    private UserMealService service;

    public UserMeal create(UserMeal userMeal) {
        LOG.info("create " + userMeal);

        return service.save(LoggedUser.id(), userMeal);
    }

    public void delete(int mealId) throws NotFoundException {
        LOG.info("delete " + mealId);

        service.delete(LoggedUser.id(), mealId);
    }

    public Collection<UserMealWithExceed> getAll() {
        LOG.info("getAll");

        return UserMealsUtil.getWithExceeded(service.getAll(LoggedUser.id()), LoggedUser.getCaloriesPerDay());
    }

    public void update(UserMeal userMeal) {
        LOG.info("update " + userMeal);

        service.update(LoggedUser.id(), userMeal);
    }

    public UserMeal get(int mealId) throws NotFoundException {
        LOG.info("get " + mealId);

        return service.get(LoggedUser.id(), mealId);
    }

    public Collection<UserMealWithExceed> getFiltered(LocalDateTime startDT, LocalDateTime endDT) {
        LOG.info("getFiltered");

        return UserMealsUtil.getWithExceeded(service.getFiltered(LoggedUser.id(), startDT, endDT), LoggedUser.getCaloriesPerDay());
    }
}
