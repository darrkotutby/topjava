package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            mealList.add(new UserMeal(LocalDateTime.of(2015, Month.MAY, i, 10, 0), "Завтрак", 300 + i * 140));
            mealList.add(new UserMeal(LocalDateTime.of(2015, Month.MAY, i, 13, 0), "Обед", 500 + i * 100));
            mealList.add(new UserMeal(LocalDateTime.of(2015, Month.MAY, i, 20, 0), "Ужин", 500 + i * 300));
        }

        List<UserMealWithExceed> list1 = getFilteredWithExceededByStream(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        List<UserMealWithExceed> list2 = getFilteredWithExceededByLoop(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        System.out.println(list1);
        System.out.println(list2);


    }

    public static List<UserMealWithExceed> getFilteredWithExceededByStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> map = new HashMap<>();
        Map<LocalDate, AtomicBoolean> mapb = new HashMap<>();
        return mealList.stream()
                .peek(userMeal -> {
                    int calories = map.getOrDefault(userMeal.getDateTime().toLocalDate(),
                            caloriesPerDay) - userMeal.getCalories();
                    map.put(userMeal.getDateTime().toLocalDate(), calories);
                    AtomicBoolean caloriesb = mapb.getOrDefault(userMeal.getDateTime().toLocalDate(),
                            new AtomicBoolean(calories < 0));
                    caloriesb.set(calories < 0);
                    mapb.put(userMeal.getDateTime().toLocalDate(), caloriesb);
                })
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExceed(userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        mapb.get(userMeal.getDateTime().toLocalDate())))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceededByLoop(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> days = new HashMap<>();
        Map<LocalDate, AtomicBoolean> daysb = new HashMap<>();
        List<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();
        for (UserMeal userMeal : mealList) {
            int calories = days.getOrDefault(userMeal.getDateTime().toLocalDate(),
                    caloriesPerDay) -userMeal.getCalories() ;
            days.put(userMeal.getDateTime().toLocalDate(), calories);
            AtomicBoolean caloriesb = daysb.getOrDefault(userMeal.getDateTime().toLocalDate(),
                    new AtomicBoolean(calories < 0));
            caloriesb.set(calories < 0);
            daysb.put(userMeal.getDateTime().toLocalDate(), caloriesb);
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExceedList.add(new UserMealWithExceed(userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        caloriesb));
            }
        }
        return userMealWithExceedList;
    }
}
