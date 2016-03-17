package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext appCtx;

    private UserMealRestController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");

        controller = appCtx.getBean(UserMealRestController.class);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        UserMeal userMeal = new UserMeal(LocalDateTime.parse(request.getParameter("dateTime")),
                                         request.getParameter("description"),
                                         Integer.valueOf(request.getParameter("calories")));

        LOG.info(userMeal.isNew() ? "Create {}" : "Update {}", userMeal);

        String id = request.getParameter("id");
        if (id.isEmpty()) {
            controller.create(userMeal);
        } else {
            controller.update(userMeal, Integer.valueOf(id));
        }

        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("mealList", controller.getAll());
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        } else if ("filter".equals(action)) {
            String startDate = getAttributedParameter("startDate", request);
            String endDate = getAttributedParameter("endDate", request);
            String startTime = getAttributedParameter("startTime", request);
            String endTime = getAttributedParameter("endTime", request);

            LOG.info("getBetween date [{}, {}], time [{}, {}]", startDate, endDate, startTime, endTime);

            request.setAttribute("mealList", controller.getBetween(startDate, endDate, startTime, endTime));
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            controller.delete(id);
            response.sendRedirect("meals");
        } else {
            final UserMeal meal = action.equals("create") ?
                    new UserMeal(LocalDateTime.now(), "", 1000) :
                    controller.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
    }

    private String getAttributedParameter(String paramName, HttpServletRequest request) {
        String paramValue = request.getParameter(paramName);
        request.setAttribute(paramName, paramValue);
        return paramValue;
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }
}
