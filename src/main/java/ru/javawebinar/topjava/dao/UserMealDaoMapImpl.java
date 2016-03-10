package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class UserMealDaoMapImpl implements UserMealDao {
    private static int nextId = 0;

    private Map<Integer, UserMeal> userMeals;
    {
        List<UserMeal> list = Arrays.asList(
                new UserMeal(nextId++, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(nextId++, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(nextId++, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(nextId++, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(nextId++, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(nextId++, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));

        userMeals = list.stream().collect(Collectors.toConcurrentMap(UserMeal::getId, Function.identity()));
    }

    @Override
    public UserMeal findUserMealById(int id) {
        return userMeals.get(id);
    }

    @Override
    public List<UserMeal> findAllUserMeals() {
        return new ArrayList<>(userMeals.values());
    }

    @Override
    public void addUserMeal(UserMeal userMeal) {
        int id = nextId++;
        userMeals.put(id, new UserMeal(id, userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories()));
    }

    @Override
    public void updateUserMeal(UserMeal userMeal) {
        UserMeal entity = userMeals.get(userMeal.getId());
        if (entity != null) {
            entity.setCalories(userMeal.getCalories());
            entity.setDescription(userMeal.getDescription());
            entity.setDateTime(userMeal.getDateTime());
        }
    }

    @Override
    public boolean deleteUserMealById(int id) {
        return userMeals.remove(id) != null;
    }
}
