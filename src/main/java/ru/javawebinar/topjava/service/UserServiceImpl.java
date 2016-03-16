package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;

import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }

    public void delete(int id) {
        ExceptionUtil.check(repository.delete(id), id);
    }

    public User get(int id) {
        return ExceptionUtil.check(repository.get(id), id);
    }

    public User getByEmail(String email) {
        return ExceptionUtil.check(repository.getByEmail(email), "User with email="
                                                            + email + " not found");
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public void update(User user) {
        ExceptionUtil.check(repository.save(user), user.getId());
    }
}
