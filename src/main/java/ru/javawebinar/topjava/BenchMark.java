package ru.javawebinar.topjava;


import org.openjdk.jmh.annotations.*;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BenchMark {

    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5)
    public void benchGetFilteredWithExceededByLoop(ExecutionPlan plan) {

        UserMealsUtil.getFilteredWithExceededByLoop(plan.mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5)
    public void getFilteredWithExceededByLoopWithAdditionalClass(ExecutionPlan plan) {

        UserMealsUtil.getFilteredWithExceededByLoopWithAdditionalClass(plan.mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5)
    public void benchGetFilteredWithExceededByStream(ExecutionPlan plan) {

        UserMealsUtil.getFilteredWithExceededByStream(plan.mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

    }
/*
    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5)
    public void benchgetFilteredWithExceededByStreamWithCustomCollector(ExecutionPlan plan) {

        UserMealsUtil.getFilteredWithExceededByStreamWithCustomCollector(plan.mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5)
    public void benchgetFilteredWithExceededByStreamWithCustomCollectorV(ExecutionPlan plan) {

        UserMealsUtil.getFilteredWithExceededByStreamWithCustomCollectorV(plan.mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

    }

    /*
    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5)
    public void benchTestL(ExecutionPlan plan) {

        UserMealsUtil.getFilteredWithExceededByLoopWithLambda(plan.mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

    } */

    @State(Scope.Benchmark)
    public static class ExecutionPlan {

        @Param({"10", "100", "1000", "10000"})
        public int iterations;
        List<UserMeal> mealList;

        @Setup(Level.Invocation)
        public void setUp() {


            Random random = new Random();

            mealList = new ArrayList<>();
            for (int i = 1; i < iterations; i++) {
                mealList.add(new UserMeal(LocalDateTime.of(2000 + i / 336, (i % 336) / 28 != 0 ? (i % 336) / 28 : 1, (i % 336 % (28)) != 0 ? (i % 336 % (28)) : 1, 10, 0), "Завтрак", 300 + i * 14));
                mealList.add(new UserMeal(LocalDateTime.of(2000 + i / 336, (i % 336) / 28 != 0 ? (i % 336) / 28 : 1, (i % 336 % (28)) != 0 ? (i % 336 % (28)) : 1, 13, 0), "Обед", 500 + i * 10));
                mealList.add(new UserMeal(LocalDateTime.of(2000 + i / 336, (i % 336) / 28 != 0 ? (i % 336) / 28 : 1, (i % 336 % (28)) != 0 ? (i % 336 % (28)) : 1, 20, 0), "Ужин", 200 + i * 13));
            }
        }
    }


}