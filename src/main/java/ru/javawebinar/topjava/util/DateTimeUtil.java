package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;

/**
 * GKislin
 * 07.01.2015.
 */
public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static final LocalDate DATE_MIN = LocalDate.of(0, 1, 1);
    public static final LocalDate DATE_MAX = LocalDate.of(3000, 1, 1);

    public static <T extends Comparable<T>> boolean isBetween(T value, T start, T end) {
        return value.compareTo(start) >= 0 && value.compareTo(end) <= 0;
    }

    public static LocalDate parseOrDefault(String date, LocalDate defaultValue) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            return defaultValue;
        }
    }

    public static LocalTime parseOrDefault(String time, LocalTime defaultValue) {
        try {
            return LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            return defaultValue;
        }
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TME_FORMATTER);
    }
}
