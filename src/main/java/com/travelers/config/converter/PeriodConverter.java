package com.travelers.config.converter;

import javax.persistence.AttributeConverter;
import java.time.Period;

public class PeriodConverter implements AttributeConverter<Period, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final Period attribute) {
        return attribute.getDays();
    }

    @Override
    public Period convertToEntityAttribute(final Integer dbData) {
        return Period.ofDays(dbData);
    }
}
