package ru.javawebinar.topjava.util;

public class Day {
    private final int caloriesPerDay;
    private int caloriesCount = 0;
    private Boolean exceeded;

    Day(int caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
        this.exceeded = true;
    }

    Day addCalories(int caloriesCount) {
        this.caloriesCount += caloriesCount;
        this.exceeded = this.caloriesCount > caloriesPerDay;
        return this;
    }

    public Boolean isExceeded() {
        return exceeded;
    }
}