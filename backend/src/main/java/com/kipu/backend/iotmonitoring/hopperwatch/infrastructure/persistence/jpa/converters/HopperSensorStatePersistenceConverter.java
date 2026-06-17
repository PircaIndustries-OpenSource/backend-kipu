package com.kipu.backend.iotmonitoring.hopperwatch.infrastructure.persistence.jpa.converters;

import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.valueobjects.HopperSensorState;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts hopper states between the domain model and persistence column values.
 */
@Converter(autoApply = false)
public class HopperSensorStatePersistenceConverter implements AttributeConverter<HopperSensorState, Integer> {

    @Override
    public Integer convertToDatabaseColumn(HopperSensorState attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public HopperSensorState convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : HopperSensorState.fromValue(dbData);
    }
}