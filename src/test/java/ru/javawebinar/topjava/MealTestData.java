package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int USER_MEAL100002_ID = START_SEQ + 2;
    private static final int USER_MEAL100003_ID = START_SEQ + 3;
    private static final int USER_MEAL100004_ID = START_SEQ + 4;
    private static final int USER_MEAL100005_ID = START_SEQ + 5;
    private static final int USER_MEAL100006_ID = START_SEQ + 6;
    private static final int USER_MEAL100007_ID = START_SEQ + 7;


    public static final Meal USER_MEAL100002 = new Meal(USER_MEAL100002_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal USER_MEAL100003 = new Meal(USER_MEAL100003_ID, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal USER_MEAL100004 = new Meal(USER_MEAL100004_ID, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal USER_MEAL100005 = new Meal(USER_MEAL100005_ID, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal USER_MEAL100006 = new Meal(USER_MEAL100006_ID, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    public static final Meal USER_MEAL100008 = new Meal(USER_MEAL100007_ID, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    private static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields().isEqualTo(expected);
    }
}
