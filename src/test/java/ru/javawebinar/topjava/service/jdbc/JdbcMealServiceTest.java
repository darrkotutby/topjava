package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;


@ActiveProfiles(profiles = {"jdbc", "postgres"})
public class JdbcMealServiceTest extends AbstractMealServiceTest {


}