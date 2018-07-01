package ru.javawebinar.topjava.repository;

import java.util.List;
import java.util.function.Predicate;

public interface Repository<T> {

    T add(T t);

    void update(T t);

    void delete(int id);

    T get(int id);

    List<T> read(Predicate<T> p);

}
