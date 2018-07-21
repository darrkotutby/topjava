package ru.javawebinar.topjava.repository.jpa;

import ru.javawebinar.topjava.model.User;

import java.io.Serializable;
import java.util.Objects;

public class MealPK implements Serializable {
    protected Integer id;
    protected User user;

    public MealPK() {}

    public MealPK(Integer id, User user) {
        this.id = id;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealPK mealPK = (MealPK) o;
        return Objects.equals(id, mealPK.id) &&
                Objects.equals(user, mealPK.user);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, user);
    }
}

