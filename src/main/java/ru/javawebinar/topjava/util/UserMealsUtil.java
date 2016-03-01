package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

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
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        //getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);

        getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(System.out::println);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field

        Map<LocalDate, Integer> calories = mealList.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(),
                                                Collectors.summingInt(UserMeal::getCalories)));


        List<UserMealWithExceed> result = mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> {
                    boolean exceed = calories.get(meal.getDateTime().toLocalDate()) > caloriesPerDay;
                    return new UserMealWithExceed(meal.getDateTime(), meal.getDescription(),
                            meal.getCalories(), exceed);
                })
                .collect(Collectors.toList());

/*
        List<UserMeal> filteredList = new ArrayList<>();
        for (UserMeal userMeal : mealList) {
            LocalDate localDate = userMeal.getDateTime().toLocalDate();
            Integer oldValue = calories.getOrDefault(localDate, 0);
            calories.put(localDate, oldValue + userMeal.getCalories());

            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                filteredList.add(userMeal);
            }
        }

        List<UserMealWithExceed> result = new ArrayList<>();
        for (UserMeal userMeal : filteredList) {
            boolean exceed = calories.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay;
            UserMealWithExceed userMealWithExceed = new UserMealWithExceed(userMeal.getDateTime(),
                    userMeal.getDescription(), userMeal.getCalories(), exceed);

            result.add(userMealWithExceed);
        }*/

        return result;
    }
}
