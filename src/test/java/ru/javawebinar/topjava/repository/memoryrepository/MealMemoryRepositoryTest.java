package ru.javawebinar.topjava.repository.memoryrepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.FillUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MealMemoryRepositoryTest {

    private MealMemoryRepository repository = MealMemoryRepository.getRepository();

    private User user1 = FillUtil.getUserList().get(0);

    private Meal meal1 = FillUtil.getMealList().get(0);
    private Meal meal2 = FillUtil.getMealList().get(1);
    private Meal meal5 = FillUtil.getMealList().get(4);
    private Meal meal8 = FillUtil.getMealList().get(7);
    private Meal meal11 = FillUtil.getMealList().get(10);


    @BeforeEach
    void init() {
        FillUtil.getMealList().forEach(meal -> repository.add(meal));
    }

    @Test
    void count() {
        assertEquals(12, repository.count());
    }

    @Test
    void clear() {
        repository.clear();
        assertEquals(0, repository.count());
    }

    @Test
    void add() {
        Meal test = new Meal(LocalDateTime.of(2015, Month.JUNE, 30, 10, 0), "Завтрак", 500, user1);
        Meal test1 = repository.add(test);
        assertEquals(test, test1);
        assertEquals(13, repository.count());
        assertTrue(repository.exists(test));
        List<Meal> meals = repository.query(m -> m.getDateTime().equals(test.getDateTime()) && m.getUser().getLogin().equalsIgnoreCase("DARRKO"));
        assertEquals(1, meals.size());
        assertEquals(test1, meals.get(0));
        assertNotEquals(0, test1.getId());
    }

    @Test
    void update() {
        Meal meal = repository.query((Meal m) -> m.equals(meal1)).get(0);
        meal.setDescription("Второй завтрак");
        meal.setCalories(2000);
        repository.update(meal);
        List<Meal> meals = repository.query(m -> m.equals(meal));
        assertEquals(1, meals.size());
        assertEquals(meal, meals.get(0));
        assertEquals(meal.getId(), meals.get(0).getId());
        assertEquals(meal.getDescription(), meals.get(0).getDescription());
        assertEquals(meal.getCalories(), meals.get(0).getCalories());
    }

    @Test
    void delete() {
        repository.delete(meal1);
        assertEquals(11, repository.count());
        assertFalse(repository.exists(meal1));
    }

    @Test
    void query() {
        List<Meal> expectedMeals = Arrays.asList(meal2, meal5, meal8, meal11);
        List<Meal> queriedMeals = repository.query((Meal m) -> m.getDescription().equalsIgnoreCase("ОБЕД"));
        assertEquals(4, queriedMeals.size());
        assertEquals(expectedMeals, queriedMeals);
    }

    @AfterEach
    void tearDown() {
        repository.clear();
    }
}