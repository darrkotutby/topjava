package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Meal extends Entity {
    private final LocalDateTime dateTime;
    private final User user;

    private String description;

    private int calories;

    public Meal(LocalDateTime dateTime, String description, int calories, User user) {
        this(dateTime, description, calories, 0, user);
    }

    public Meal(LocalDateTime dateTime, String description, int calories, int id, User user) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return Objects.equals(dateTime, meal.dateTime) &&
                Objects.equals(user, meal.user);
    }

    @Override
    public int hashCode() {

        return Objects.hash(dateTime, user);
    }

    @Override
    public String toString() {
        return "Meal{" +
                "dateTime=" + dateTime +
                ", user=" + user +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                "} " + super.toString();
    }
}
