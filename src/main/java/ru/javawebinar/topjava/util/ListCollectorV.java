package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceedI;
import ru.javawebinar.topjava.model.UserMealWithExceedL;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ListCollectorV implements Collector<UserMeal, List<UserMealWithExceedI>, List<UserMealWithExceedI>> {
    private int caloriesPerDay;
    private LocalTime startTime;
    private LocalTime endTime;

    private Map<LocalDate, Counter> map;

    public ListCollectorV(int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        this.caloriesPerDay = caloriesPerDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.map = new HashMap<>();
    }

    @Override
    public Supplier<List<UserMealWithExceedI>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<UserMealWithExceedI>, UserMeal> accumulator() {
        return (list, userMeal) -> {
            LocalDate localDate = userMeal.getDateTime().toLocalDate();
            map.put(localDate, map.getOrDefault(localDate, new Counter(caloriesPerDay)).add( - userMeal.getCalories()));

            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                LocalDate date = userMeal.getDate();
                list.add(new UserMealWithExceedI(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), map.get(date)));
            }
        };
    }

    @Override
    public BinaryOperator<List<UserMealWithExceedI>> combiner() {
        return (l, r) -> {
            l.addAll(r);
            return l;
        };
    }

    @Override
    public Function<List<UserMealWithExceedI>, List<UserMealWithExceedI>> finisher() {
        return s -> {
            return s;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.CONCURRENT);
    }
}