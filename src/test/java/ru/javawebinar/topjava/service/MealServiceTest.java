package ru.javawebinar.topjava.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@ActiveProfiles("jdbc")
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.of(2016, Month.MAY, 30, 10, 0), "Завтрак", 500);
        Meal created = service.create(newMeal, 100000);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(100000), newMeal, meal6, meal5, meal4, meal3, meal2, meal1);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateMailCreate() throws Exception {
        service.create(new Meal(null, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500), 100000);
    }

    @Test
    public void delete() throws Exception {
        service.delete(TEST_MEAL_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), meal6, meal5, meal4, meal3, meal2);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotExist() throws Exception {
        service.delete(TEST_MEAL_ID + 1000, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteWrongUser() throws Exception {
        service.delete(TEST_MEAL_ID, ADMIN_ID);
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(TEST_MEAL_ID, USER_ID);
        assertMatch(meal, meal1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotExist() throws Exception {
        Meal meal = service.get(TEST_MEAL_ID + 1000, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getWrongUser() throws Exception {
        Meal meal = service.get(TEST_MEAL_ID, ADMIN_ID);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), meal6, meal5, meal4, meal3, meal2, meal1);
    }

    @Test
    public void getAllWrongUser() {
        assertEquals(0, service.getAll(USER_ID + 100).size());
    }

    @Test
    public void update() {
        Meal updated = new Meal(meal1);
        updated.setCalories(10);
        updated.setDescription("Test");
        service.update(updated, USER_ID);
        assertMatch(service.get(TEST_MEAL_ID, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotExist() {
        Meal newMeal = new Meal(TEST_MEAL_ID + 1000, LocalDateTime.of(2016, Month.MAY, 30, 10, 0), "Завтрак", 500);
        service.update(newMeal, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateWrongUser() {
        Meal updated = new Meal(meal1);
        updated.setCalories(10);
        updated.setDescription("Test");
        service.update(updated, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        assertMatch(service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30), LocalDate.of(2015, Month.MAY, 30), USER_ID), meal3, meal2, meal1);
    }

    @Test
    public void getBetweenDatesNotFound() {
        assertEquals(0, service.getBetweenDates(LocalDate.of(2015, Month.MAY, 29), LocalDate.of(2015, Month.MAY, 29), USER_ID).size());
    }

    @Test
    public void getBetweenWrongUser() {
        assertEquals(0, service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30), LocalDate.of(2015, Month.MAY, 30), USER_ID + 100).size());
    }

    @Test
    public void getBetweenDateTimes() {
        assertMatch(service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 30, 11, 0),
                LocalDateTime.of(2015, Month.MAY, 30, 14, 0),
                USER_ID), meal2);
    }

    @Test
    public void getBetweenDateTimesNotFound() {
        assertEquals(0, service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 30, 23, 0),
                LocalDateTime.of(2015, Month.MAY, 30, 23, 30),
                USER_ID).size());
    }

    @Test
    public void getBetweenDateTimesWrongUser() {
        assertEquals(0, service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 30, 11, 0),
                LocalDateTime.of(2015, Month.MAY, 30, 14, 0),
                USER_ID + 100).size());
    }

}
