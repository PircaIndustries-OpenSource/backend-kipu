package com.kipu.backend.iotmonitoring.geolocalization.infrastructure.persistence.jpa.converters;

import com.kipu.backend.iotmonitoring.geolocalization.domain.model.valueobjects.GeolocalizationSensorState;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts geolocalization states between the domain model and persistence column values.
 */
@Converter(autoApply = true) // autoApply = true hace que JPA lo aplique automáticamente a cualquier campo de este tipo
public class GeolocalizationSensorStatePersistenceConverter implements AttributeConverter<GeolocalizationSensorState, Integer> {

    @Override
    public Integer convertToDatabaseColumn(GeolocalizationSensorState attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public GeolocalizationSensorState convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : GeolocalizationSensorState.fromInteger(dbData);
    }
}