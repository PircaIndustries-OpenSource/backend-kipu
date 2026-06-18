package com.kipu.backend.iotmonitoring.geolocalization.infrastructure.persistence.jpa.converters;

import com.kipu.backend.iotmonitoring.geolocalization.domain.model.valueobjects.SensorId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SensorIdPersistenceConverter implements AttributeConverter<SensorId, String> {

    @Override
    public String convertToDatabaseColumn(SensorId attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public SensorId convertToEntityAttribute(String dbData) {
        return dbData == null || dbData.isBlank() ? null : new SensorId(dbData);
    }
}
