package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
        userMeal.setId(null);

        int userId = LoggedUser.id();

        LOG.info("create " + userMeal + " for user with id=" + userId);

        return service.save(userId, userMeal);
    }

    public void delete(int mealId) throws NotFoundException {
        int userId = LoggedUser.id();

        LOG.info("delete " + mealId + " for user with id=" + userId);

        service.delete(userId, mealId);
    }

    public List<UserMealWithExceed> getAll() {
        int userId = LoggedUser.id();

        LOG.info("getAll for user with id=" + userId);

        return UserMealsUtil.getWithExceeded(service.getAll(userId), LoggedUser.getCaloriesPerDay());
    }

    public void update(UserMeal userMeal, int mealId) {
        userMeal.setId(mealId);

        int userId = LoggedUser.id();

        LOG.info("update " + userMeal + " for user with id=" + userId);

        service.update(userId, userMeal);
    }

    public UserMeal get(int mealId) throws NotFoundException {
        int userId = LoggedUser.id();

        LOG.info("get " + mealId + " for user with id=" + userId);

        return service.get(userId, mealId);
    }

    public List<UserMealWithExceed> getBetween(String startDate, String endDate,
                                               String startTime, String endTime) {

        LocalDate startD = DateTimeUtil.parseOrDefault(startDate, DateTimeUtil.DATE_MIN);
        LocalDate endD = DateTimeUtil.parseOrDefault(endDate, DateTimeUtil.DATE_MAX);
        LocalTime startT = DateTimeUtil.parseOrDefault(startTime, LocalTime.MIN);
        LocalTime endT = DateTimeUtil.parseOrDefault(endTime, LocalTime.MAX);

        int userId = LoggedUser.id();

        LOG.info("getBetween startDate: " + startD + "\nendDate: " + endD +
                 "\nstartTime: " + startT + "\nendTime: " + endT + " for user with id=" + userId);

        return UserMealsUtil.getFilteredWithExceeded(service.getBetweenDates(userId, startD, endD), startT, endT, LoggedUser.getCaloriesPerDay());
    }
}
