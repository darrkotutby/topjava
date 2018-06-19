package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceedL;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ListCollector implements Collector<UserMeal, List<UserMealWithExceedL>, List<UserMealWithExceedL>> {
    private int caloriesPerDay;
    private LocalTime startTime;
    private LocalTime endTime;

    private Map<LocalDate, Integer> map;

    public ListCollector(int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        this.caloriesPerDay = caloriesPerDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.map = new HashMap<>();
    }

    @Override
    public Supplier<List<UserMealWithExceedL>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<UserMealWithExceedL>, UserMeal> accumulator() {
        return (list, userMeal) -> {
            LocalDate localDate = userMeal.getDateTime().toLocalDate();
            map.put(localDate, map.getOrDefault(localDate, caloriesPerDay) - userMeal.getCalories());

            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                list.add(new UserMealWithExceedL(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), () -> map.get(userMeal.getDate()) < 0));
            }
        };
    }

    @Override
    public BinaryOperator<List<UserMealWithExceedL>> combiner() {
        return (l, r) -> {
            l.addAll(r);
            return l;
        };
    }

    @Override
    public Function<List<UserMealWithExceedL>, List<UserMealWithExceedL>> finisher() {
        return s -> {
            return s;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.CONCURRENT);
    }
}