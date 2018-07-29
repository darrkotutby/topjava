package ru.javawebinar.topjava.repository.jdbc.converter;

import java.time.LocalDateTime;

public class PostgresConverter implements Converter<LocalDateTime, LocalDateTime> {
    @Override
    public LocalDateTime convert(LocalDateTime localDateTime) {
        return localDateTime;
    }
}
