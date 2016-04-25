package ru.javawebinar.topjava.web.meal;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = UserMealAjaxController.AJAX_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserMealAjaxController extends AbstractUserMealController {
    public static final String AJAX_URL = "/ajax/profile/meals";

    private static final String DATE_TIME_PATTERN = "";

    @RequestMapping(method = RequestMethod.GET)
    public List<UserMealWithExceed> getAll() {
        return super.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void save(@RequestParam("id") int id,
                     @RequestParam("dateTime") LocalDateTime dateTime,
                     @RequestParam("description") String description,
                     @RequestParam("calories") int calories) {

        UserMeal meal = new UserMeal(id, dateTime, description, calories);

        if (id == 0) {
            super.create(meal);
        } else {
            super.update(meal, id);
        }
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public List<UserMealWithExceed> getBetween(
            @RequestParam(value = "startDate", required = false) LocalDate startDate, @RequestParam(value = "startTime", required = false) LocalTime startTime,
            @RequestParam(value = "endDate", required = false) LocalDate endDate, @RequestParam(value = "endTime", required = false) LocalTime endTime) {
        return super.getBetween(
                startDate != null ? startDate : TimeUtil.MIN_DATE, startTime != null ? startTime : LocalTime.MIN,
                endDate != null ? endDate : TimeUtil.MAX_DATE, endTime != null ? endTime : LocalTime.MAX);
    }
}
