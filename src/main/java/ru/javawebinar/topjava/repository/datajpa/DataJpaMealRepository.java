package ru.javawebinar.topjava.repository.datajpa;

import ru.javawebinar.topjava.model.Meal;

public interface DataJpaMealRepository {
    Meal getWithUser(int id, int userId);
}
