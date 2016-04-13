package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private static final ResultSetExtractor<List<User>> RESULT_SET_EXTRACTOR = rs -> {
        Map<Integer, User> map = new LinkedHashMap<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            Role role = Role.valueOf(rs.getString("role"));

            User user = map.get(id);
            if (user != null) {
                user.addRole(role);
            } else {
                user = new User(id, rs.getString("name"), rs.getString("email"), rs.getString("password"),
                                rs.getInt("calories_per_day"), rs.getBoolean("enabled"), role);
                map.put(user.getId(), user);
            }
        }
        return new ArrayList<>(map.values());
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;
    private SimpleJdbcInsert insertRoles;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("id");

        this.insertRoles = new SimpleJdbcInsert(dataSource)
                .withTableName("USER_ROLES");
    }

    @Override
    @Transactional
    public User save(User user) {
        MapSqlParameterSource userMap = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("registered", user.getRegistered())
                .addValue("enabled", user.isEnabled())
                .addValue("caloriesPerDay", user.getCaloriesPerDay());

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(userMap);
            user.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", userMap);

            jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());
        }

        MapSqlParameterSource rolesMap = new MapSqlParameterSource().addValue("user_id", user.getId());
        for (Role role : user.getRoles()) {
            rolesMap.addValue("role", role.toString());
            insertRoles.execute(rolesMap);
        }

        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {

        User user = getByCriterion("id", id);
        if (user == null) {
            return null;
        }

        return withRoles(user);
    }

    @Override
    public User getByEmail(String email) {

        User user = getByCriterion("email", email);
        if (user == null) {
            return null;
        }

        return withRoles(user);
    }

    private User getByCriterion(String criterionName, Object criterionValue) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE " + criterionName + "=?", ROW_MAPPER, criterionValue);
        return DataAccessUtils.singleResult(users);
    }

    private User withRoles(User user) {
        List<String> roles = jdbcTemplate.queryForList("SELECT ur.role FROM user_roles ur WHERE ur.user_id=?", String.class, user.getId());
        user.setRoles(roles.stream().map(Role::valueOf).collect(Collectors.toSet()));
        return user;
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users u LEFT JOIN user_roles ur ON u.id=ur.user_id ORDER BY name, email", RESULT_SET_EXTRACTOR);
    }
}
