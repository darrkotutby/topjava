package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(m -> save(m, m.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {} userId={}", meal, userId);

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        Meal testMeal = repository.get(meal.getId());

        if (testMeal == null || testMeal.getUserId() != userId) {
            return null;
        }
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("save {} userId={}", id, userId);
        Meal meal = repository.get(id);

        if (meal == null || meal.getUserId() != userId) {
            return false;
        }

        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {} userId={}", id, userId);
        Meal meal = repository.get(id);

        if (meal == null || meal.getUserId() != userId) {
            return null;
        }
        return meal;
    }

   /* @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll userId={}", userId);
        return getAll(userId, null, null);
    } */

    @Override
    public List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getAll userId={} startDate={} endDate={}", userId, startDate, endDate);

        LocalDate sd = startDate == null ? LocalDate.MIN : startDate;
        LocalDate ed = endDate == null ? LocalDate.MAX : endDate;

        return repository.values().stream().filter(m -> m.getUserId() == userId && DateTimeUtil.isBetween(m.getDateTime().toLocalDate(), sd, ed))
                .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime())).collect(Collectors.toList());
    }


}

