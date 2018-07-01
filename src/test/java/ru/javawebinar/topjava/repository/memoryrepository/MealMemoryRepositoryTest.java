package ru.javawebinar.topjava.repository.memoryrepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.FillUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MealMemoryRepositoryTest {

    private final MealMemoryRepository repository = new MealMemoryRepository();

    private final List<Meal> mealList = FillUtil.getMealList();
    private Meal meal1;

    @BeforeEach
    void init() {
        mealList.forEach(meal -> repository.add(meal));
        meal1 = repository.get(1);
    }

    @Test
    void add() {
        Meal test = new Meal(LocalDateTime.of(2015, Month.JUNE, 30, 10, 0), "Завтрак", 500);
        Meal test1 = repository.add(test);
        assertEquals(test, test1);
        assertEquals(7, repository.count());
        assertNotEquals(0, test1.getId());
    }

    @Test
    void update() {
        Meal meal = repository.get(1);
        meal.setDescription("Второй завтрак");
        meal.setCalories(2000);
        repository.update(meal);
        Meal queriedMeal = repository.get(meal.getId());
        assertEquals(meal, queriedMeal);
        assertEquals(meal.getDescription(), queriedMeal.getDescription());
        assertEquals(meal.getCalories(), queriedMeal.getCalories());
    }

    @Test
    void delete() {
        repository.delete(meal1.getId());
        assertEquals(5, repository.count());
        assertNull(repository.get(meal1.getId()));
    }

    @AfterEach
    void tearDown() {
        repository.clear();
    }
}