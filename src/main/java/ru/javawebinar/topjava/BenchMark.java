package ru.javawebinar.topjava;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import org.openjdk.jmh.annotations.*;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BenchMark {

    @State(Scope.Benchmark)
    public static class ExecutionPlan {

        List<UserMeal> mealList;

        @Param({ "20" })
        public int iterations;

        @Setup(Level.Invocation)
        public void setUp() {


            Random random = new Random();

            mealList = new ArrayList<>();
            for (int i=1; i<iterations; i++) {
                mealList.add(new UserMeal(LocalDateTime.of(2015, Month.MAY, i, 10, 0), "Завтрак", 300+i*14));
                mealList.add(new UserMeal(LocalDateTime.of(2015, Month.MAY, i, 13, 0), "Обед", 500+i*10));
                mealList.add(new UserMeal(LocalDateTime.of(2015, Month.MAY, i, 20, 0), "Ужин", 200+i*13));
            }
        }
    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 5)
    public void benchGetFilteredWithExceededByStream(ExecutionPlan plan) {

        UserMealsUtil.getFilteredWithExceededByStream(plan.mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 5)
    public void benchGetFilteredWithExceededByLoop(ExecutionPlan plan) {

        UserMealsUtil.getFilteredWithExceededByLoop(plan.mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

    }

}