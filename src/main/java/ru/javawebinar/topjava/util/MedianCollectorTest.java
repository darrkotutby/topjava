package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MedianCollectorTest implements Collector<UserMeal, HashMap<LocalDate, Integer>, List<UserMealWithExceed>> {
    private int caloriesPerDay;
    private LocalTime startTime;
    private LocalTime endTime;

    private List<UserMeal> list;

    public MedianCollectorTest(int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        this.caloriesPerDay = caloriesPerDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.list = new ArrayList<>();
    }

    @Override
    public Supplier<HashMap<LocalDate, Integer>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<HashMap<LocalDate, Integer>, UserMeal> accumulator() {
        return (map, userMeal) -> {
            LocalDate localDate = userMeal.getDateTime().toLocalDate();
            map.put(localDate, map.getOrDefault(localDate, caloriesPerDay) - userMeal.getCalories());
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                list.add(userMeal);
            }
        };
    }

    @Override
    public BinaryOperator<HashMap<LocalDate, Integer>> combiner() {
        return (l, r) -> {
            l.putAll(r);
            return l;
        };
    }

    @Override
    public Function<HashMap<LocalDate, Integer>, List<UserMealWithExceed>> finisher() {
        return s -> {
            return list.stream().map(userMeal -> new UserMealWithExceed(userMeal.getDateTime(),
                    userMeal.getDescription(),
                    userMeal.getCalories(),
                    s.get(userMeal.getDateTime().toLocalDate()) < 0)).collect(Collectors.toList());
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.CONCURRENT);
    }
}