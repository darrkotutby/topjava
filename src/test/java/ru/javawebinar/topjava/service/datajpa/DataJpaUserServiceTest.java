package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import javax.xml.ws.Service;
import java.util.Arrays;
import java.util.Collections;


@ActiveProfiles(profiles = Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void getWithMeals() {
        User expectedUser =  new User(UserTestData.ADMIN);
        User actualUser = service.getWithMeals(UserTestData.ADMIN_ID);
        UserTestData.assertMatch(actualUser, expectedUser);
        MealTestData.assertMatch(actualUser.getMeals(), Arrays.asList(MealTestData.ADMIN_MEAL1, MealTestData.ADMIN_MEAL2));
    }

    @Test
    public void getWithMealsForUserWithoutMeal() {
        User expectedUser = new User(UserTestData.ADMIN);
        expectedUser.setId(null);
        expectedUser.setEmail("tt@gmail.com");
        expectedUser = service.create(expectedUser);
        User actualUser = service.getWithMeals(expectedUser.getId());
        UserTestData.assertMatch(actualUser, expectedUser);
        MealTestData.assertMatch(actualUser.getMeals(), Collections.emptyList());
    }

}