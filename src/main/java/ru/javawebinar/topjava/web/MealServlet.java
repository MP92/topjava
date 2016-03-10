package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.exception.UserMealNotFoundException;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserMealServiceImpl;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    UserMealService service = new UserMealServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to MealServlet.doPost");

        request.setCharacterEncoding("UTF-8");

        String url = request.getRequestURL().substring(request.getRequestURL().lastIndexOf("/"));

        if ("/meals-create".equals(url)) {
            request.getRequestDispatcher("/meal-form.jsp").forward(request, response);
            return;
        }

        Integer id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            request.setAttribute("errorMsg", "Parameter 'id' not found");
            request.getRequestDispatcher("/error-page.jsp").forward(request, response);
            return;
        }

        try {
            if ("/meals-edit".equals(url)) {
                UserMeal userMeal = service.findUserMealById(id);
                if (userMeal != null) {
                    request.setAttribute("userMeal", userMeal);
                    request.getRequestDispatcher("/meal-form.jsp").forward(request, response);
                    return;
                }

                throw new UserMealNotFoundException();
            }
            else if ("/meals-delete".equals(url)) {
                if (service.deleteUserMealById(id)) {
                    request.setAttribute("message", "UserMeal with id = '" + id + "' deleted");
                } else {
                    throw new UserMealNotFoundException();
                }
            }
            else if ("/meals-confirm".equals(url)) {
                String description = request.getParameter("description");
                LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
                int calories = Integer.parseInt(request.getParameter("calories"));
                UserMeal userMeal = new UserMeal(id, dateTime, description, calories);

                if (id >= 0) {
                    service.updateUserMeal(userMeal);
                    request.setAttribute("message", "UserMeal with id = '" + id + "' updated");
                } else {
                    service.addUserMeal(userMeal);
                    request.setAttribute("message", "UserMeal was added");
                }
            }

            request.getRequestDispatcher("/success.jsp").forward(request, response);

        } catch (UserMealNotFoundException e) {
            request.setAttribute("errorMsg", "UserMeal with id=" + id + " not found");
            request.getRequestDispatcher("/error-page.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to MealServlet.doGet");

        List<UserMeal> userMeals = service.findAllUserMeals();
        List<UserMealWithExceed> userMealsWithExceed = UserMealsUtil.getFilteredMealsWithExceeded(
                userMeals, LocalTime.MIN, LocalTime.MAX, 2000);

        request.setAttribute("userMeals", userMealsWithExceed);

        request.getRequestDispatcher("/meal-list.jsp").forward(request, response);
    }
}
