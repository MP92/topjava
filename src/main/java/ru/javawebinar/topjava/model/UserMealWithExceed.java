package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * GKislin
 * 11.01.2015.
 */
public class UserMealWithExceed {
    private final int id;

    protected final LocalDateTime dateTime;

    protected final String description;

    protected final int calories;

    protected final boolean exceed;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public UserMealWithExceed(int id, LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.id = id;

        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }

    @Override
    public String toString() {
        return "UserMealWithExceed{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getFormattedDateTime() {
        return dateTime.format(formatter);
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExceed() {
        return exceed;
    }

    public int getId() {
        return id;
    }
}
