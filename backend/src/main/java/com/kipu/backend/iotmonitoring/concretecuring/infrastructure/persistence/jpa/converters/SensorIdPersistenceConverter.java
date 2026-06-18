package com.kipu.backend.iotmonitoring.concretecuring.infrastructure.persistence.jpa.converters;


import com.kipu.backend.iotmonitoring.concretecuring.domain.model.valueobjects.SensorId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts sensor identifiers between the domain model and persistence column values.
 */
@Converter(autoApply = false)
public class SensorIdPersistenceConverter implements AttributeConverter<SensorId, String> {

    @Override
    public String convertToDatabaseColumn(SensorId attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public SensorId convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new SensorId(dbData);
    }
}
