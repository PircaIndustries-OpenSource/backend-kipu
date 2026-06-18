package com.kipu.backend.iotmonitoring.seismiccontrol.infrastructure.persistence.jpa.converters;

import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.valueobjects.SeismicControlSensorState;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts hopper states between the domain model and persistence column values.
 */
@Converter(autoApply = false)
public class SeismicControlStatePersistenceConverter implements AttributeConverter<SeismicControlSensorState, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SeismicControlSensorState attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public SeismicControlSensorState convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : SeismicControlSensorState.fromInteger(dbData);
    }
}