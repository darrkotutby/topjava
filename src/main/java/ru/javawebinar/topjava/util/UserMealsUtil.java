package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
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
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        System.out.println("TODO return filtered list with correctly exceeded field");


        /*
        // Map<LocalDate, Integer> map = new HashMap<>();
        //  mealList.forEach(meal -> map.put(meal.getDateTime().toLocalDate(), map.getOrDefault(meal.getDateTime().toLocalDate(), 0) + meal.getCalories()));


        Map<LocalDate, Integer> map =
                mealList.stream().collect(Collectors.groupingBy(UserMeal :: getDate,
                        Collectors.summingInt(UserMeal::getCalories)));


        userMealWithExceedList = mealList.stream()
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExceed(userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        map.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());     */


        List<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();

        Map<LocalDate, Integer> map = new HashMap<>();
        for (UserMeal userMeal : mealList) {
            map.put(userMeal.getDateTime().toLocalDate(), map.getOrDefault(userMeal.getDateTime().toLocalDate(), 0)+userMeal.getCalories());

        }


        for (UserMeal userMeal : mealList) {
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExceedList.add(new UserMealWithExceed(userMeal.getDateTime(),userMeal.getDescription(), userMeal.getCalories(), map.get(userMeal.getDateTime().toLocalDate())>caloriesPerDay));
            }

        }


        return userMealWithExceedList;
    }



}
