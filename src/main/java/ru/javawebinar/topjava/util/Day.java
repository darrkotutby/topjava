package ru.javawebinar.topjava.util;

public class Day {
    private final int caloriesPerDay;
    private int caloriesCount = 0;

    Day(int caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }

    Day addCalories(int caloriesCount) {
        this.caloriesCount += caloriesCount;
        return this;
    }

    public boolean isExceeded() {
        return caloriesCount > caloriesPerDay;
    }
}