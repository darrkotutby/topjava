package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.Day;

import java.time.LocalDateTime;


public class UserMealWithExceed {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final Day day;

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, Day day) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.day = day;
    }

    public boolean isExceeded() {
        return day.isExceeded();
    }
}
