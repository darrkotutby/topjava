package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;


public class UserMealWithExceedL {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final MyFunction exceeded;

    public UserMealWithExceedL(LocalDateTime dateTime, String description, int calories, MyFunction exceeded) {
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
                ", isExceed =" + exceeded.get() +
                '}';
    }
}
