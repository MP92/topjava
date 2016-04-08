package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer> {

    @Transactional
    @Modifying
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    @Transactional
    UserMeal save(UserMeal entity);

    UserMeal get(@Param("id") int id, @Param("userId") int userId);

    List<UserMeal> getAll(@Param("userId") int userId);

    List<UserMeal> getBetween(@Param("userId") int userId, @Param("startDate") LocalDateTime startDate,
                              @Param("endDate") LocalDateTime endDate);

    @Transactional
    @Modifying
    @Query("SELECT m FROM UserMeal m LEFT JOIN FETCH m.user WHERE m.id=?1 AND m.user.id=?2")
    UserMeal getOneWithUser(int id, int userId);
}
