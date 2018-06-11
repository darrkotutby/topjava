package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(23, 0), 2000);
    }

    private static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Day> days = new HashMap<>();
        List<UserMealWithExceed> userMealWithExceedList;


        /*for (UserMeal userMeal : mealList) {
            days.put(userMeal.getDateTime().toLocalDate(), days.getOrDefault(userMeal.getDateTime().toLocalDate(),
                    new Day(caloriesPerDay)).addCalories(userMeal.getCalories()));
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExceedList.add(new UserMealWithExceed(userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        days.get(userMeal.getDateTime().toLocalDate())));
            }
        }*/

        userMealWithExceedList = mealList.stream()
                .peek(userMeal -> days.put(userMeal.getDateTime().toLocalDate(),
                        days.getOrDefault(userMeal.getDateTime().toLocalDate(), new Day(caloriesPerDay))
                                .addCalories(userMeal.getCalories())))
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal ->
                        new UserMealWithExceed(userMeal.getDateTime(),
                                userMeal.getDescription(),
                                userMeal.getCalories(),
                                days.get(userMeal.getDateTime().toLocalDate())))
                .collect(Collectors.toList());

        return userMealWithExceedList;
    }
}
