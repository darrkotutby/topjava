package ru.javawebinar.topjava.repository.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractServiceTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(profiles = {"datajpa"})
public class DataJpaUserRepositoryImplTest extends AbstractServiceTest {

    @Autowired
    DataJpaUserRepository dataJpaUserRepositoryImpl;


    @Test
    public void getWithMeals() {
        User expectedUser = clone(UserTestData.ADMIN);
        User actualUser = dataJpaUserRepositoryImpl.getWithMeals(UserTestData.ADMIN_ID);
        UserTestData.assertMatch(actualUser, expectedUser);
        MealTestData.assertMatch(actualUser.getMeals(), Arrays.asList(MealTestData.ADMIN_MEAL1, MealTestData.ADMIN_MEAL2));

    }

    private User clone(User user) {
        return new User(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getCaloriesPerDay(), user.isEnabled(), user.getRegistered(), user.getRoles());
    }

}