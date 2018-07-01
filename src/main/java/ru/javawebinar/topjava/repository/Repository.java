package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Entity;

import java.util.List;
import java.util.function.Predicate;

public interface Repository<T extends Entity> {
    int count();

    void clear();

    T add(T t);

    void update(T t);

    void delete(T t);

    boolean exists(T t);

    List<T> query(Predicate<T> predicate);

    T getById(int id);

    T getByPk(T t);
}
