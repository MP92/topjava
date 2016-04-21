package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.AbstractMealControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static ru.javawebinar.topjava.MealTestData.*;

/**
 * Created by Максим on 20.04.2016.
 */
public class UserMealRestControllerTest extends AbstractMealControllerTest {

    public static final String REST_URL = UserMealRestController.REST_URL + '/';


    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), service.getAll(LoggedUser.id()));
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(USER_MEALS_WITH_EXCEED));
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal updated = getUpdated();

        mockMvc.perform(put(REST_URL + MEAL1_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, service.get(MEAL1_ID, LoggedUser.id()));
    }

    @Test
    public void testCreate() throws Exception {
        UserMeal expected = getCreated();

        ResultActions action = mockMvc.perform(post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(expected)))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        UserMeal actual = MATCHER.fromJsonAction(action);
        expected.setId(actual.getId());
        List<UserMeal> expectedList = Arrays.asList(actual, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);

        MATCHER.assertEquals(expected, actual);
        MATCHER.assertCollectionEquals(expectedList, service.getAll(LoggedUser.id()));
    }

    @Test
    public void testGetBetween() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + "filter")
                    .param("startDate", "2015-05-31")
                    .param("startTime", "13:00:00.000")
                    .param("endDate", "2015-05-31")
                    .param("endTime", "20:00:00.000")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(USER_MEALS_WITH_EXCEED.subList(0, 2)));
    }
}