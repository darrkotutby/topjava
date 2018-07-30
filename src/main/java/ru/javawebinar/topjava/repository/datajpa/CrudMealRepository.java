package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Modifying
    @Transactional(propagation=Propagation.REQUIRED)
    int deleteByIdAndUser_Id(Integer id, Integer user_id);

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    Meal save(Meal meal);

    Optional<Meal> findByIdAndUserId(Integer id, Integer user_id);

    List<Meal> findByUserId(Sort sort, Integer user_id);

    List<Meal> findByDateTimeBetweenAndUserId(Sort sort, LocalDateTime startDate, LocalDateTime endDate, Integer userId);
}
