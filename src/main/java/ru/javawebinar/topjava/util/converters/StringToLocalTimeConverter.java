package ru.javawebinar.topjava.util.converters;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;

/**
 * Created by Максим on 20.04.2016.
 */
public class StringToLocalTimeConverter implements Converter<String, LocalTime> {
    @Override
    public LocalTime convert(String source) {
        return LocalTime.parse(source);
    }
}
