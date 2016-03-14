package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class UserMealServiceImpl implements UserMealService {

    private static final Logger LOG = LoggerFactory.getLogger(UserMealServiceImpl.class);

    @Autowired
    private UserMealRepository repository;

    @Override
    public UserMeal save(int userId, UserMeal userMeal) {
        return repository.save(userId, userMeal);
    }

    @Override
    public void delete(int userId, int id) throws NotFoundException {
        ExceptionUtil.check(repository.delete(userId, id), id);
    }

    @Override
    public UserMeal get(int userId, int id) throws NotFoundException {
        return ExceptionUtil.check(repository.get(userId, id), id);
    }

    @Override
    public Collection<UserMeal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public Collection<UserMeal> getFiltered(int userId, LocalDateTime startDT, LocalDateTime endDT) {
        return repository.getFiltered(userId, startDT, endDT);
    }

    @Override
    public void update(int userId, UserMeal userMeal) {
        repository.save(userId, userMeal);
    }
}
