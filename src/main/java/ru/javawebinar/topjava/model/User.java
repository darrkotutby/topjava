package ru.javawebinar.topjava.model;

import java.util.Objects;

public class User extends Entity {
    private String login;

    private String fullName;

    private int caloriesPerDate;

    public User(String login, String fullName, int caloriesPerDate) {
        this(login, fullName, caloriesPerDate, 0);
    }

    public User(String login, String fullName, int caloriesPerDate, int id) {
        super(id);
        this.login = login;
        this.fullName = fullName;
        this.caloriesPerDate = caloriesPerDate;
    }

    public User() {
        super(0);
    }

    public String getLogin() {
        return login;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getCaloriesPerDate() {
        return this.caloriesPerDate;
    }

    public void setCaloriesPerDate(int caloriesPerDate) {
        this.caloriesPerDate = caloriesPerDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login.toUpperCase(), user.login.toUpperCase());
    }

    @Override
    public int hashCode() {

        return Objects.hash(login.toUpperCase());
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", fullName='" + fullName + '\'' +
                ", caloriesPerDate=" + caloriesPerDate +
                "} " + super.toString();
    }
}
