package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.model.UserMealWithExceedI;
import ru.javawebinar.topjava.model.UserMealWithExceedL;

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
        List<UserMealWithExceed> list1 = getFilteredWithExceededByStream(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        List<UserMealWithExceed> list2 = getFilteredWithExceededByLoop(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        List<UserMealWithExceedI> list3 = getFilteredWithExceededByLoopI(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        System.out.println(list1);
        System.out.println(list2);
        System.out.println(list3);

        List<UserMealWithExceedL> list5 = Test(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        System.out.println(list5);
    }

    public static List<UserMealWithExceedL> Test(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        return mealList.stream()
                .collect(new ListCollector(caloriesPerDay, startTime, endTime));
    }

    public static List<UserMealWithExceedL> TestL(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> days = new HashMap<>();
        List<UserMealWithExceedL> userMealWithExceedList = new ArrayList<>();
        for (UserMeal userMeal : mealList) {
            days.put(userMeal.getDateTime().toLocalDate(), days.getOrDefault(userMeal.getDateTime().toLocalDate(),
                    caloriesPerDay) - userMeal.getCalories());
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExceedList.add(new UserMealWithExceedL(userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        () -> days.get(userMeal.getDateTime().toLocalDate()) < 0));
            }
        }
        return userMealWithExceedList;
    }


    public static List<UserMealWithExceed> getFilteredWithExceededByStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> map =
                mealList.stream().collect(Collectors.groupingBy(UserMeal::getDate,
                        Collectors.summingInt(UserMeal::getCalories)));
        return mealList.stream()
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExceed(userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        map.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceededByLoop(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> days = new HashMap<>();
        for (UserMeal userMeal : mealList) {
            days.put(userMeal.getDateTime().toLocalDate(), days.getOrDefault(userMeal.getDateTime().toLocalDate(),
                    0) + userMeal.getCalories());
        }
        List<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();
        for (UserMeal userMeal : mealList) {
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExceedList.add(new UserMealWithExceed(userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        days.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }
        return userMealWithExceedList;
    }

    public static List<UserMealWithExceedI> getFilteredWithExceededByLoopI(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Counter> days = new HashMap<>();
        List<UserMealWithExceedI> userMealWithExceedListI = new ArrayList<>();
        for (UserMeal userMeal : mealList) {
            days.put(userMeal.getDateTime().toLocalDate(), days.getOrDefault(userMeal.getDateTime().toLocalDate(),
                    new Counter(caloriesPerDay)).add(-userMeal.getCalories()));
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExceedListI.add(new UserMealWithExceedI(userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        days.get(userMeal.getDateTime().toLocalDate())));
            }
        }
        return userMealWithExceedListI;
    }

}
