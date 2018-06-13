package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;


public class UserMealWithExceed {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final AtomicInteger exceeded;

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, AtomicInteger exceeded) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceeded = exceeded;
    }

    public String toString() {
        return "UserMealWithExceed{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", isExceed =" + exceeded +
                '}';
    }
}
