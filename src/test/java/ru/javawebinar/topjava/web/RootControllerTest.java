package ru.javawebinar.topjava.web;

import org.junit.jupiter.api.Test;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

class RootControllerTest extends AbstractControllerTest {

    @Test
    void testUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users", hasSize(2)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(START_SEQ)),
                                hasProperty("name", is(USER.getName()))
                        )
                )));
    }

    @Test
    void testMeals() throws Exception {
        mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("meals"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"))
                .andExpect(model().attribute("meals", hasSize(6)))
                .andExpect(model().attribute("meals", hasItem(
                        allOf(
                                hasProperty("id", is(MEAL1_ID)),
                                hasProperty("dateTime", is(MEAL1.getDateTime())),
                                hasProperty("description", is(MEAL1.getDescription())),
                                hasProperty("calories", is(MEAL1.getCalories())),
                                hasProperty("exceed", is(false))
                        )
                )))
                .andExpect(model().attribute("meals", hasItem(
                        allOf(
                                hasProperty("id", is(MEAL2.getId())),
                                hasProperty("dateTime", is(MEAL2.getDateTime())),
                                hasProperty("description", is(MEAL2.getDescription())),
                                hasProperty("calories", is(MEAL2.getCalories())),
                                hasProperty("exceed", is(false))
                        )
                )))
                .andExpect(model().attribute("meals", hasItem(
                        allOf(
                                hasProperty("id", is(MEAL3.getId())),
                                hasProperty("dateTime", is(MEAL3.getDateTime())),
                                hasProperty("description", is(MEAL3.getDescription())),
                                hasProperty("calories", is(MEAL3.getCalories())),
                                hasProperty("exceed", is(false))
                        )
                )))
                .andExpect(model().attribute("meals", hasItem(
                        allOf(
                                hasProperty("id", is(MEAL4.getId())),
                                hasProperty("dateTime", is(MEAL4.getDateTime())),
                                hasProperty("description", is(MEAL4.getDescription())),
                                hasProperty("calories", is(MEAL4.getCalories())),
                                hasProperty("exceed", is(true))
                        )
                )))
                .andExpect(model().attribute("meals", hasItem(
                        allOf(
                                hasProperty("id", is(MEAL5.getId())),
                                hasProperty("dateTime", is(MEAL5.getDateTime())),
                                hasProperty("description", is(MEAL5.getDescription())),
                                hasProperty("calories", is(MEAL5.getCalories())),
                                hasProperty("exceed", is(true))
                        )
                )))
                .andExpect(model().attribute("meals", hasItem(
                        allOf(
                                hasProperty("id", is(MEAL6.getId())),
                                hasProperty("dateTime", is(MEAL6.getDateTime())),
                                hasProperty("description", is(MEAL6.getDescription())),
                                hasProperty("calories", is(MEAL6.getCalories())),
                                hasProperty("exceed", is(true))
                        )
                )));
    }

    @Test
    void testMeals1() throws Exception {

        List<MealWithExceed> list = MealsUtil.getWithExceeded(mealService.getAll(UserTestData.USER_ID), UserTestData.USER.getCaloriesPerDay());

        mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("meals"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"))
                .andExpect(model().attribute("meals", hasSize(6)))
                .andExpect(model().attribute("meals", MealsUtil.getWithExceeded(mealService.getAll(USER_ID), USER.getCaloriesPerDay())))
        //.andExpect(model().attribute("meals", MealsUtil.getWithExceeded(mealService.getAll(USER.getId()), USER.getCaloriesPerDay())))
        //.andExpect(model().attribute("meals", contentJson(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1)))
        ;

        // model().getModelAndView


    }


}


