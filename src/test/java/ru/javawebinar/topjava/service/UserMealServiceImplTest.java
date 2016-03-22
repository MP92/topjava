package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceImplTest {

    @Autowired
    private UserMealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() {
        UserMeal userMeal = service.get(USER_MEAL_FIRST_ID, USER_ID);
        MATCHER.assertEquals(USER_MEAL_FIRST, userMeal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() {
        UserMeal meal = service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotOwn() {
        UserMeal meal = service.get(USER_MEAL_FIRST_ID, ADMIN_ID);
    }

    @Test
    public void testDelete() {
        service.delete(USER_MEAL_FIRST_ID, USER_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(USER_MEAL_SECOND), service.getAll(USER_ID));
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_MEAL_ORDERED_FIRST, ADMIN_MEAL_ORDERED_SECOND), service.getAll(ADMIN_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() {
        service.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotOwn() {
        service.delete(USER_MEAL_FIRST_ID, ADMIN_ID);
    }

    @Test
    public void testGetBetweenDateTimesAllMatches() {
        Collection<UserMeal> meals = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 30, 10, 0),
                LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), USER_ID);

        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL_ORDERED_FIRST, USER_MEAL_ORDERED_SECOND), meals);
    }

    @Test
    public void testGetBetweenDateTimesPartialMatches() {
        Collection<UserMeal> meals = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 30, 11, 0),
                LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), USER_ID);

        MATCHER.assertCollectionEquals(Collections.singletonList(USER_MEAL_SECOND), meals);
    }

    @Test
    public void testGetBetweenDateTimesNoMatches() {
        Collection<UserMeal> meals = service.getBetweenDateTimes(LocalDateTime.of(2000, 1, 1, 0, 0), 
                                        LocalDateTime.of(2000, 2, 1, 0, 0), USER_ID);

        MATCHER.assertCollectionEquals(Collections.emptyList(), meals);
    }

    @Test
    public void testGetBetweenDateTimesNoOwnMatches() {
        Collection<UserMeal> meals = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 31, 23, 59),
                LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), USER_ID);

        MATCHER.assertCollectionEquals(Collections.emptyList(), meals);
    }

    @Test
    public void testGetAll() {
        Collection<UserMeal> meals = service.getAll(USER_ID);

        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL_ORDERED_FIRST, USER_MEAL_ORDERED_SECOND), meals);
    }

    @Test
    public void testUpdate() {
        UserMeal userMeal = new UserMeal(USER_MEAL_FIRST_ID, LocalDateTime.now(), "Bongo bong", 555);
        service.update(userMeal, USER_ID);
        UserMeal actualMeal = service.get(USER_MEAL_FIRST_ID, USER_ID);
        MATCHER.assertEquals(userMeal, actualMeal);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateWithoutId() {
        UserMeal userMeal = new UserMeal(LocalDateTime.now(), "Bongo bong", 555);
        service.update(userMeal, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() {
        UserMeal userMeal = new UserMeal(1, LocalDateTime.now(), "Bongo bong", 555);
        service.update(userMeal, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotOwn() {
        UserMeal userMeal = new UserMeal(USER_MEAL_FIRST_ID, LocalDateTime.now(), "Bongo bong", 555);
        service.update(userMeal, ADMIN_ID);
    }

    @Test
    public void testSave() {
        UserMeal userMeal = new UserMeal(LocalDateTime.now(), "Bongo bong", 555);
        UserMeal createdMeal = service.save(userMeal, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(createdMeal, USER_MEAL_ORDERED_FIRST, USER_MEAL_ORDERED_SECOND), service.getAll(USER_ID));
    }
}