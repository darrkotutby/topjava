package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.javawebinar.topjava.repository.jdbc.converter.Converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class HsqldbJdbcMealRepositoryImpl extends AbstractJdbcMealRepository<Timestamp> {

    public HsqldbJdbcMealRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Timestamp convert(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }
}
