package ru.javawebinar.topjava.util.converters;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

/**
 * Created by Максим on 20.04.2016.
 */
public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        return LocalDate.parse(source);
    }
}
