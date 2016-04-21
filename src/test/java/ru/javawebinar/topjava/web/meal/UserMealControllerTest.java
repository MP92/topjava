package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.web.AbstractMealControllerTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static ru.javawebinar.topjava.MealTestData.*;

import static ru.javawebinar.topjava.util.UserMealsUtil.*;

/**
 * Created by Максим on 19.04.2016.
 */
public class UserMealControllerTest extends AbstractMealControllerTest {

    @Test
    public void testMealList() throws Exception {

        MvcResult result = mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("mealList"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/mealList.jsp"))
                .andExpect(model().attribute("mealList", hasSize(6)))
                .andReturn();

        Collection<UserMealWithExceed> actualList = (Collection<UserMealWithExceed>)result.getRequest().getAttribute("mealList");
        MATCHER_WITH_EXCEED.assertCollectionEquals(actualList, USER_MEALS_WITH_EXCEED);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(get("/meals/delete").param("id", Integer.toString(MEAL1_ID)))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/meals"))
                .andExpect(redirectedUrl("/meals"));
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), service.getAll(LoggedUser.id()));
    }

    @Test
    public void testEditForUpdate() throws Exception {
        mockMvc.perform(get("/meals/update").param("id", Integer.toString(MEAL1_ID)))
                .andExpect(status().isOk())
                .andExpect(view().name("mealEdit"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/mealEdit.jsp"))
                .andExpect(model().attribute("meal", MEAL1));
    }

    @Test
    public void testEditForCreate() throws Exception {
        mockMvc.perform(get("/meals/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("mealEdit"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/mealEdit.jsp"))
                .andExpect(model().attributeExists("meal"));
    }

    @Test
    public void testGetBetween() throws Exception {
        MvcResult result =
                mockMvc.perform(
                    post("/meals/filter")
                        .param("startDate", "2015-05-31")
                        .param("endDate", "2015-05-31")
                        .param("startTime", "10:00")
                        .param("endTime", "20:00")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("mealList"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/mealList.jsp"))
                .andExpect(model().attribute("mealList", hasSize(3)))
                .andReturn();

        Collection<UserMealWithExceed> actualList = (Collection<UserMealWithExceed>)result.getRequest().getAttribute("mealList");
        MATCHER_WITH_EXCEED.assertCollectionEquals(actualList, getWithExceeded(Arrays.asList(MEAL6, MEAL5, MEAL4), DEFAULT_CALORIES_PER_DAY));
    }

    @Test
    public void testUpdateOrCreate() throws Exception {
        String dt = "2020-06-15T13:45:30";
        String description = "updated description";
        int calories = 123;
        UserMeal updated = new UserMeal(MEAL1_ID, LocalDateTime.parse(dt), description, calories);

        mockMvc.perform(
                post("/meals")
                    .param("id", Integer.toString(MEAL1_ID))
                    .param("dateTime", dt)
                    .param("description", description)
                    .param("calories", Integer.toString(calories))
                )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/meals"))
                .andExpect(redirectedUrl("/meals"));

        MATCHER.assertEquals(updated, service.get(MEAL1_ID, LoggedUser.id()));
    }
}