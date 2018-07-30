package ru.javawebinar.topjava.repository.datajpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractServiceTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ActiveProfiles(profiles = {"datajpa"})
public class DataJpaMealRepositoryImplTest extends AbstractServiceTest {

    @Autowired
    DataJpaMealRepository dataJpaMealRepositoryImpl;

    @Test
    public void getWithUserTest() {
        Meal expectedMeal = clone(MealTestData.MEAL1, UserTestData.USER);
        Meal meal = dataJpaMealRepositoryImpl.getWithUser(MealTestData.MEAL1_ID, UserTestData.USER_ID);
        assertThat(meal).isEqualToComparingFieldByField(expectedMeal);

    }

    private Meal clone(Meal meal, User user) {
        Meal clonedMeal = new Meal(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
        clonedMeal.setUser(user);
        return clonedMeal;
    }


}