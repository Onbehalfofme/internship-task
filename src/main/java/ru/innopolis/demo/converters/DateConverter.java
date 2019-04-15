package ru.innopolis.demo.converters;

import javax.persistence.AttributeConverter;
import java.sql.Date;
import java.time.LocalDate;

/**
 * this class is used for converting date into required form
 */
public class DateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate attribute) {
        return attribute != null ? Date.valueOf(attribute) : null;
    }

    @Override
    public LocalDate convertToEntityAttribute(Date dbData) {
        return dbData != null ? dbData.toLocalDate() : null;
    }
}

