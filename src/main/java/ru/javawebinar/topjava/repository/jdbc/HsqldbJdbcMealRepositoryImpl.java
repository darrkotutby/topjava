package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Profile(Profiles.HSQL_DB)
@Repository
public class HsqldbJdbcMealRepositoryImpl extends AbstractJdbcMealRepository<Timestamp> {

    @Override
    public Timestamp convert(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }
}
