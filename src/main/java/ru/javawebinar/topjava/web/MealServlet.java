package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.List;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    UserMealService service = new UserMealServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to MealServlet.doPost");

        request.setCharacterEncoding("UTF-8");

        String sId = request.getParameter("id");
        Integer id = !sId.isEmpty() ? Integer.parseInt(sId) : null;
        String description = request.getParameter("description");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        int calories = Integer.parseInt(request.getParameter("calories"));

        UserMeal userMeal = new UserMeal(id, dateTime, description, calories);
        service.saveUserMeal(userMeal);

        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to MealServlet.doGet");

        String action = request.getParameter("action");
        if (action == null) {
            LOG.debug("Get List");

            List<UserMealWithExceed> userMealsWithExceed = UserMealsUtil.getWithExceeded(service.findAllUserMeals());

            request.setAttribute("meals", userMealsWithExceed);

            request.getRequestDispatcher("/meal-list.jsp").forward(request, response);
        } else if ("create".equals(action)) {
            LOG.debug("Create");

            request.getRequestDispatcher("/meal-form.jsp").forward(request, response);
        } else {
            Integer id = Integer.parseInt(Objects.requireNonNull(request.getParameter("id")));

            if ("delete".equals(action)) {
                LOG.debug("Delete", id);

                service.deleteUserMealById(id);

                response.sendRedirect("meals");
            } else {
                LOG.debug("Update", id);

                request.setAttribute("meal", service.findUserMealById(id));

                request.getRequestDispatcher("/meal-form.jsp").forward(request, response);
            }
        }
    }
}
