package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;

public class SpringMain {
    public static void main(String[] args) {
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {

            appCtx.getEnvironment().addActiveProfile(Profiles.getActiveDbProfile());
            appCtx.getEnvironment().addActiveProfile(Profiles.REPOSITORY_IMPLEMENTATION);
            appCtx.load("classpath:spring/spring-app.xml","classpath:spring/spring-db.xml" );
            appCtx.refresh();

            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName1", "email1@mail.ru", "password1", Role.ROLE_ADMIN));
            System.out.println(adminUserController.getAll());

            MealRestController mealController = appCtx.getBean(MealRestController.class);
            mealController.create(new Meal(null, of(2018, Month.MAY, 30, 10, 0), "Завтрак", 500));
            System.out.println(mealController.getAll());
            List<MealWithExceed> filteredMealsWithExceeded =
                    mealController.getBetween(
                            LocalDate.of(2018, Month.MAY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2018, Month.MAY, 31), LocalTime.of(11, 0));
            filteredMealsWithExceeded.forEach(System.out::println);
        }
    }
}
