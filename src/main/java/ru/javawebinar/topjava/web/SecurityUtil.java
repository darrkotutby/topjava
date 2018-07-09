package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    private static ThreadLocal<Integer> id;

    static {
        id = new ThreadLocal<>();
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }

    public static Integer authUserId() {
        return id.get();
    }

    public static void setUserId(Integer usrId) {
        id.set(usrId);
    }
}