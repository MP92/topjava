package ru.javawebinar.topjava.web.meal;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.repository.UserMealRepository;

import java.util.Arrays;

import static ru.javawebinar.topjava.MealTestData.*;

public class InMemoryUserMealRestControllerTest {

    private static ConfigurableApplicationContext appCtx;
    private static UserMealRestController controller;

    @BeforeClass
    public static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app-test.xml");
        System.out.println("\n" + Arrays.toString(appCtx.getBeanDefinitionNames()) + "\n");
        controller = appCtx.getBean(UserMealRestController.class);
    }

    @Before
    public void setUp() throws Exception {
        // Re-initialize
        UserMealRepository repository = appCtx.getBean(UserMealRepository.class);
        repository.getAll(USER_ID).forEach(meal -> repository.delete(meal.getId(), USER_ID));
        repository.getAll(ADMIN_ID).forEach(meal -> repository.delete(meal.getId(), ADMIN_ID));
        repository.save(USER_MEAL_FIRST, USER_ID);
        repository.save(USER_MEAL_SECOND, USER_ID);
        repository.save(ADMIN_MEAL_FIRST, ADMIN_ID);
        repository.save(ADMIN_MEAL_SECOND, ADMIN_ID);
    }

    @AfterClass
    public static void afterClass() {
        appCtx.close();
    }
}