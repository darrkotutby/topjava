package ru.javawebinar.topjava.repository.datajpa;

import ru.javawebinar.topjava.model.User;

public interface DataJpaUserRepository {
    User getWithMeals(int id);
}
