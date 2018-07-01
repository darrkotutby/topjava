package ru.javawebinar.topjava.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static DateTimeFormatter getFormatter() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm:ss");
        return f;
    }

}
