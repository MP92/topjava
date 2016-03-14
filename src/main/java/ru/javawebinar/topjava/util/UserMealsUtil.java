package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static final List<UserMeal> MEAL_LIST = Arrays.asList(
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static void main(String[] args) {
        List<UserMealWithExceed> filteredMealsWithExceeded = getFilteredWithExceeded(MEAL_LIST, LocalTime.of(7, 0), LocalTime.of(12, 0), DEFAULT_CALORIES_PER_DAY);
        filteredMealsWithExceeded.forEach(System.out::println);

        System.out.println(getFilteredWithExceeded(MEAL_LIST, LocalTime.of(7, 0), LocalTime.of(12, 0), DEFAULT_CALORIES_PER_DAY));
    }


    public static List<UserMealWithExceed> getWithExceeded(Collection<UserMeal> mealList, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream()
                .collect(
                        Collectors.groupingBy(um -> um.getDateTime().toLocalDate(),
                                Collectors.summingInt(UserMeal::getCalories))
                );

        return mealList.stream()
                .map(um -> createWithExceed(um, caloriesSumByDate.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMeal> getFiltered(Collection<UserMeal> mealList, LocalDateTime startDT, LocalDateTime endDT) {
        return mealList.stream()
                .filter(um -> DateTimeUtil.isBetween(um.getDateTime(), startDT, endDT))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(Collection<UserMeal> mealList, LocalDateTime startDT, LocalDateTime endDT, int caloriesPerDay) {
        return getWithExceeded(getFiltered(mealList, startDT, endDT), caloriesPerDay);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(Collection<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return getFilteredWithExceeded(mealList, startTime.atDate(LocalDate.MAX), endTime.atDate(LocalDate.MAX), caloriesPerDay);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(Collection<UserMeal> mealList, LocalDate startDate, LocalDate endDate, int caloriesPerDay) {
        return getFilteredWithExceeded(mealList, startDate.atTime(LocalTime.MIDNIGHT), endDate.atTime(LocalTime.MIDNIGHT), caloriesPerDay);
    }


    private static UserMealWithExceed createWithExceed(UserMeal um, boolean exceeded) {
        return new UserMealWithExceed(um.getId(), um.getDateTime(), um.getDescription(), um.getCalories(), exceeded);
    }
}
