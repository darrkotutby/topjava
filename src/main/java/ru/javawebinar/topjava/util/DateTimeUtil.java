package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  /*  public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static boolean isBetween(LocalDate ld, LocalDate startDate, LocalDate endDate) {
        return ld.compareTo(startDate) >= 0 && ld.compareTo(endDate) <= 0;
    } */

    public static <T extends Comparable<T>> boolean isBetween(T ld, T start, T end) {
        if (start == null && end == null) {
            return true;
        }
        if (start == null) {
            return ld.compareTo(end) <= 0;
        }
        if (end == null) {
            return ld.compareTo(start) >= 0;
        }
        return ld.compareTo(start) >= 0 && ld.compareTo(end) <= 0;
    }


    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
