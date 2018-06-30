package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class MealWithExceed {
    public final LocalDateTime dateTime;
    public final String description;
    public final int calories;
    public final User user;
    public final boolean exceed;
    public int id;

    public MealWithExceed(int id, LocalDateTime dateTime, String description, int calories, User user, boolean exceed) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.user = user;
        this.exceed = exceed;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public User getUser() {
        return user;
    }

    public boolean isExceed() {
        return exceed;
    }

    @Override
    public String toString() {
        return "MealWithExceed{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", user=" + user +
                ", exceed=" + exceed +
                '}';
    }
}
