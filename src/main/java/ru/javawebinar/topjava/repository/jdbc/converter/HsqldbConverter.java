package ru.javawebinar.topjava.repository.jdbc.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class HsqldbConverter implements Converter<LocalDateTime, Timestamp> {
    @Override
    public Timestamp convert(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }
}
