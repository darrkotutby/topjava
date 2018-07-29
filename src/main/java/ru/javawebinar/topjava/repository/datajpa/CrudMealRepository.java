package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Modifying
    @Transactional
    int deleteByIdAndUser_Id(Integer id, Integer user_id);

    @Override
    @Transactional
    Meal save(Meal meal);

    Optional<Meal> findByIdAndUser_Id(Integer id, Integer user_id);

    List<Meal> findByUser_Id(Sort sort, Integer user_id);

    List<Meal> findByDateTimeBetweenAndUser_Id(Sort sort, LocalDateTime startDate, LocalDateTime endDate, Integer userId);
}
