package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.function.Predicate;

public interface Repository<T> {

    T add(T t);

    void update(T t);

    void delete(int id);

    T get(int id);

    List<Meal> getAll();

    List<T> query(Predicate<T> p);

}
