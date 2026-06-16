package com.kipu.backend.iotmonitoring.concretecuring.infrastructure.persistence.jpa.converters;

import com.kipu.backend.iotmonitoring.concretecuring.domain.model.valueobjects.CuringSensorState;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts CuringState between the domain model and persistence column values.
 */
@Converter(autoApply = false)
public class ConcreteCuringSensorStatePersistenceConverter implements AttributeConverter<CuringSensorState, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CuringSensorState attribute) {
        if (attribute == null) return null;
        return switch (attribute) {
            case INACTIVE -> 0;
            case MONITORING -> 1;
            case ALERT -> 2;
        };
    }

    @Override
    public CuringSensorState convertToEntityAttribute(Integer dbData) {
        if (dbData == null) return null;
        return switch (dbData) {
            case 0 -> CuringSensorState.INACTIVE;
            case 1 -> CuringSensorState.MONITORING;
            case 2 -> CuringSensorState.ALERT;
            default -> throw new IllegalArgumentException("Unknown database value for CuringSensorState: " + dbData);
        };
    }
}