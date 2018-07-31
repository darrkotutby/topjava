package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    int deleteByIdAndUserId(Integer id, Integer user_id);

    Optional<Meal> findByIdAndUserId(Integer id, Integer user_id);

    List<Meal> findByUserId(Sort sort, Integer user_id);

    List<Meal> findByDateTimeBetweenAndUserId(Sort sort, LocalDateTime startDate, LocalDateTime endDate, Integer userId);


    @Query("SELECT m FROM Meal m JOIN FETCH m.user WHERE m.id = (:id) and m.user.id = (:user_id)")
    Meal findByIdAndFetchUserEagerly(@Param("id") Integer id, @Param("user_id") Integer userId);

}
