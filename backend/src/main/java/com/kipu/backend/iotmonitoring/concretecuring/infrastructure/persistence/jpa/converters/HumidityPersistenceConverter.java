package com.kipu.backend.iotmonitoring.concretecuring.infrastructure.persistence.jpa.converters;

import com.kipu.backend.iotmonitoring.concretecuring.domain.model.valueobjects.Humidity;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts humidity between the domain model and persistence column values.
 */
@Converter(autoApply = false)
public class HumidityPersistenceConverter implements AttributeConverter<Humidity, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Humidity attribute) {
        return attribute == null ? null : attribute.percentage();
    }

    @Override
    public Humidity convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : new Humidity(dbData);
    }
}