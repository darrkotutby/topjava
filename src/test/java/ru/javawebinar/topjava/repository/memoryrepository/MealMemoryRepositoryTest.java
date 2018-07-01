package ru.javawebinar.topjava.repository.memoryrepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.exception.ExistsException;
import ru.javawebinar.topjava.repository.exception.NotExistsException;
import ru.javawebinar.topjava.util.FillUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MealMemoryRepositoryTest {

    private MealMemoryRepository repository = MealMemoryRepository.getRepository();

    private List<User> userList = FillUtil.getUserList();
    private User user1 = userList.get(0);

    private List<Meal> mealList = FillUtil.getMealList(userList);
    private Meal meal1 = mealList.get(0);
    private Meal meal2 = mealList.get(1);
    private Meal meal5 = mealList.get(4);
    private Meal meal8 = mealList.get(7);
    private Meal meal11 = mealList.get(10);


    @BeforeEach
    void init() {
        mealList.forEach(meal -> repository.add(meal));
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
    void addExisting() {
        assertThrows(ExistsException.class, () -> repository.add(meal1));
        assertEquals(12, repository.count());
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
    void updateNotExisting() {
        Meal test = new Meal(LocalDateTime.of(2018, Month.MAY, 30, 10, 0), "Завтрак", 500, user1);
        assertThrows(NotExistsException.class, () -> repository.delete(test));
        assertEquals(12, repository.count());
    }

    @Test
    void delete() {
        repository.delete(meal1);
        assertEquals(11, repository.count());
        assertFalse(repository.exists(meal1));
    }

    @Test
    void deleteNotExisting() {
        Meal test = new Meal(LocalDateTime.of(2018, Month.MAY, 30, 10, 0), "Завтрак", 500, user1);
        assertThrows(NotExistsException.class, () -> repository.delete(test));
        assertEquals(12, repository.count());
    }

    @Test
    void query() {
        List<Meal> expectedMeals = Arrays.asList(meal2, meal5, meal8, meal11);
        List<Meal> queriedMeals = repository.query((Meal m) -> m.getDescription().equalsIgnoreCase("ОБЕД"));
        assertEquals(4, queriedMeals.size());
        assertEquals(expectedMeals, queriedMeals);
    }

    @Test
    void getById() {
        Meal expectedMeal = repository.getByPk(meal1);
        Meal queriedMeal = repository.getById(expectedMeal.getId());
        assertEquals(expectedMeal, queriedMeal);
        assertEquals(expectedMeal.getDateTime(), queriedMeal.getDateTime());
        assertEquals(expectedMeal.getCalories(), queriedMeal.getCalories());
    }

    @Test
    void getByIdNotFound() {
        assertThrows(NotExistsException.class, () -> repository.getById(500));
    }

    @Test
    void getByPk() {
        Meal queriedMeal = repository.getByPk(meal1);
        assertEquals(meal1, queriedMeal);
        assertEquals(meal1.getDateTime(), queriedMeal.getDateTime());
        assertEquals(meal1.getCalories(), queriedMeal.getCalories());
    }

    @Test
    void getByPkNotFound() {
        Meal test = new Meal(LocalDateTime.of(2018, Month.MAY, 30, 10, 0), "Завтрак", 500, user1);
        assertThrows(NotExistsException.class, () -> repository.getByPk(test));

    }

    @AfterEach
    void tearDown() {
        repository.clear();
    }
}