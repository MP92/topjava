package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryUserRepositoryImpl implements UserRepository {

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        save(new User("Adam", "adam123@gmail.com", "1234" , Role.ROLE_USER));
        save(new User("Eve", "eve456@gmail.com", "4321" , Role.ROLE_USER));
        save(new User("Hmm", "hmm@gmail.com", "123" , Role.ROLE_USER));
        save(new User("Heisenberg", "saymyname@gmail.com", "321" , Role.ROLE_USER));
        save(new User("Admin", "admin@admin.com", "12345" , Role.ROLE_ADMIN));
        save(new User("God", "oh@mygod.com", "123456" , Role.ROLE_ADMIN, Role.ROLE_USER));
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public User get(int id) {
        return repository.get(id);
    }

    @Override
    public User getByEmail(String email) {
        Optional<User> value = repository.values().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst();

        return value.isPresent() ? value.get() : null;
    }

    @Override
    public List<User> getAll() {
        return repository.values().stream()
                .sorted((u1, u2) -> u1.getName().compareTo(u2.getName()))
                .collect(Collectors.toList());
    }
}