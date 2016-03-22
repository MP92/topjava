package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.Month;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final int USER_MEAL_FIRST_ID = 200000;
    public static final int USER_MEAL_SECOND_ID = 200001;
    public static final int ADMIN_MEAL_FIRST_ID = 200002;
    public static final int ADMIN_MEAL_SECOND_ID = 200003;

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);

    public static final UserMeal USER_MEAL_FIRST = new UserMeal(USER_MEAL_FIRST_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final UserMeal USER_MEAL_SECOND = new UserMeal(USER_MEAL_SECOND_ID, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final UserMeal ADMIN_MEAL_FIRST = new UserMeal(ADMIN_MEAL_FIRST_ID, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static final UserMeal ADMIN_MEAL_SECOND = new UserMeal(ADMIN_MEAL_SECOND_ID, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500);

    public static final UserMeal USER_MEAL_ORDERED_FIRST = USER_MEAL_SECOND;
    public static final UserMeal USER_MEAL_ORDERED_SECOND = USER_MEAL_FIRST;
    public static final UserMeal ADMIN_MEAL_ORDERED_FIRST = ADMIN_MEAL_SECOND;
    public static final UserMeal ADMIN_MEAL_ORDERED_SECOND = ADMIN_MEAL_FIRST;
}
