package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalTimeFormatter implements Formatter<LocalTime> {

    private String pattern;

    public LocalTimeFormatter(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String print(LocalTime time, Locale locale) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    public LocalTime parse(String formatted, Locale locale) throws ParseException {
        if (formatted.length() == 0) {
            return null;
        }
        return LocalTime.parse(formatted, DateTimeFormatter.ofPattern(pattern));
    }


}