package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.service.UserMealService;

/**
 * Created by Максим on 19.04.2016.
 */
abstract public class AbstractMealControllerTest extends AbstractControllerTest {
    @Autowired
    protected UserMealService service;
}
