package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(m -> save(m, m.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {} userId={}", meal, userId);
        Map<Integer, Meal> map = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            return map.computeIfAbsent(meal.getId(), (i) -> meal);
        }
        Meal testMeal = map.get(meal.getId());
        if (testMeal == null || testMeal.getUserId() != userId) {
            return null;
        }
        return map.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("save {} userId={}", id, userId);
        Map<Integer, Meal> map = repository.get(userId);
        if (map == null) {
            return false;
        }
        Meal meal = map.get(id);
        return meal != null && meal.getUserId() == userId && repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {} userId={}", id, userId);
        Map<Integer, Meal> map = repository.get(userId);
        if (map == null) {
            return null;
        }
        Meal meal = map.get(id);
        if (meal == null || meal.getUserId() != userId) {
            return null;
        }
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll userId={}", userId);
        return getAllWithPredicate(userId, m -> true);
    }

    @Override
    public List<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getAll userId={} startDate={} endDate={}", userId, startDate, endDate);
        return getAllWithPredicate(userId, m -> DateTimeUtil.isBetween(m.getDateTime().toLocalDate(), startDate, endDate));
    }

    private List<Meal> getAllWithPredicate(int userId, Predicate<? super Meal> predicate) {
        return repository.get(userId).values().stream().filter(predicate).sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
    }


}

