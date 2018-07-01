package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class FillUtil {

    public static List<User> getUserList() {
        return Arrays.asList(
                new User("Darrko", "Test1", 3000),
                new User("AWP", "Test2", 2000));
    }

    public static List<Meal> getMealList(List<User> list) {

        User user1 = list.get(0);
        User user2 = list.get(1);

        return Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, user1),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000, user1),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500, user1),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000, user1),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500, user1),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510, user1),

                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 600, user2),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 700, user2),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 800, user2),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 100, user2),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 1600, user2),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 900, user2)
        );
    }
}
