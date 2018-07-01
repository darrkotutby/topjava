package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class MealsUtil {
    public static void main(String[] args) {
        List<Meal> meals = FillUtil.getMealList(FillUtil.getUserList());

        List<MealWithExceed> mealsWithExceeded = getFilteredWithExceeded(meals, LocalTime.of(7, 0), LocalTime.of(12, 0));
        mealsWithExceeded.forEach(System.out::println);

        System.out.println(getFilteredWithExceededByCycle(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<MealWithExceed> getFilteredWithExceeded(List<Meal> meals, LocalTime startTime, LocalTime endTime) {
        Map<User, Map<LocalDate, Integer>> caloriesSumByUserByDate = meals.stream()
                .collect(Collectors.groupingBy(Meal::getUser,
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                ));

        return meals.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> createWithExceed(meal, caloriesSumByUserByDate.get(meal.getUser()).get(meal.getDate()) > meal.getUser().getCaloriesPerDate()))
                .collect(toList());
    }

    public static List<MealWithExceed> getFilteredWithExceededByCycle(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        // final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        // meals.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        Map<User, Map<LocalDate, Integer>> caloriesSumByUserByDate = new HashMap<>();
        for (Meal meal : meals) {
            Map<LocalDate, Integer> caloriesSumByDate = caloriesSumByUserByDate.getOrDefault(meal.getUser(), new HashMap<>());
            caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum);
            caloriesSumByUserByDate.put(meal.getUser(), caloriesSumByDate);
        }


        final List<MealWithExceed> mealsWithExceeded = new ArrayList<>();
        meals.forEach(meal -> {
            if (TimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                mealsWithExceeded.add(createWithExceed(meal, caloriesSumByUserByDate.get(meal.getUser()).get(meal.getDate()) > caloriesPerDay));
            }
        });
        return mealsWithExceeded;
    }

    public static MealWithExceed createWithExceed(Meal meal, boolean exceeded) {
        return new MealWithExceed(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), meal.getUser(), exceeded);
    }
}