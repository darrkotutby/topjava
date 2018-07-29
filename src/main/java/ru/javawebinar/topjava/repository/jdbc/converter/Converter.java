package ru.javawebinar.topjava.repository.jdbc.converter;

public  interface  Converter<T, C> {
    C convert(T t);
}
