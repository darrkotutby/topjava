package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;


public class UserMealWithExceed {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final Boolean exceeded;

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceeded) {
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
