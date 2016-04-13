package ru.javawebinar.topjava.web.meal;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Controller
public class UserMealUIController extends AbstractUserMealController {

    @RequestMapping(value = {"/mealUpdate", "/mealCreate"}, method = RequestMethod.GET)
    public String showEditForm(HttpServletRequest request) {

        UserMeal meal;
        if (request.getRequestURI().endsWith("Update")) {
            meal = get(getId(request));
        } else {
            meal = new UserMeal(LocalDateTime.now(), "", 1000);
        }
        request.setAttribute("meal", meal);

        return "mealEdit";
    }

    @RequestMapping(value = "/mealSave", method = RequestMethod.POST)
    public String save(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");

        final UserMeal userMeal = new UserMeal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        if (request.getParameter("id").isEmpty()) {
            create(userMeal);
        } else {
            update(userMeal, getId(request));
        }

        return "redirect:meals";
    }

    @RequestMapping(value = "/mealDelete", method = RequestMethod.GET)
    public String delete(HttpServletRequest request) {
        super.delete(getId(request));

        return "redirect:meals";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("mealList", getAll());

        return "mealList";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String filteredList(HttpServletRequest request) {
        LocalDate startDate = TimeUtil.parseLocalDate(resetParam("startDate", request));
        LocalDate endDate = TimeUtil.parseLocalDate(resetParam("endDate", request));
        LocalTime startTime = TimeUtil.parseLocalTime(resetParam("startTime", request));
        LocalTime endTime = TimeUtil.parseLocalTime(resetParam("endTime", request));

        List<UserMealWithExceed> list = getBetween(startDate, startTime, endDate, endTime);
        request.setAttribute("mealList", list);

        return "mealList";
    }


    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"), "parameter id  must not be null");
        return Integer.valueOf(paramId);
    }

    private String resetParam(String param, HttpServletRequest request) {
        String value = request.getParameter(param);
        request.setAttribute(param, value);
        return value;
    }
}
