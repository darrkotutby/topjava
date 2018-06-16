package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.Counter;

import java.time.LocalDateTime;


public class UserMealWithExceedI {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final Counter exceeded;

    public UserMealWithExceedI(LocalDateTime dateTime, String description, int calories, Counter exceeded) {
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
