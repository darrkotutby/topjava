package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles(profiles = Profiles.DATAJPA)
public class DataJpaMealServiceTest extends AbstractMealServiceTest {

    @Test
    public void getWithUser() {
        Meal expectedMeal = clone(MealTestData.MEAL1, UserTestData.USER);
        Meal meal = service.getWithUser(MealTestData.MEAL1_ID, UserTestData.USER_ID);
        assertThat(meal).isEqualToComparingFieldByField(expectedMeal);

    }

    private Meal clone(Meal meal, User user) {
        Meal clonedMeal = new Meal(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
        clonedMeal.setUser(user);
        return clonedMeal;
    }
}