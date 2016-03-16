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
        userMeal.setId(null);

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

    public Collection<UserMealWithExceed> getFiltered(String startDate, String endDate,
                                                      String startTime, String endTime) {

        LocalDate startD = DateTimeUtil.parseOrDefault(startDate, LocalDate.MIN);
        LocalDate endD = DateTimeUtil.parseOrDefault(endDate, LocalDate.MAX);
        LocalTime startT = DateTimeUtil.parseOrDefault(startTime, LocalTime.MIN);
        LocalTime endT = DateTimeUtil.parseOrDefault(endTime, LocalTime.MAX);

        LOG.info("getFiltered startDate: " + startD + "\nendDate: " + endD +
                 "\nstartTime: " + startT + "\nendTime: " + endT);

        return UserMealsUtil.getFilteredWithExceeded(service.getFiltered(LoggedUser.id(), startD, endD), startT, endT, LoggedUser.getCaloriesPerDay());
    }
}
