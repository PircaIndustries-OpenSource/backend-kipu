package com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.domain.model.valueobjects.MeasureUnit;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MeasureUnitAttributeConverter implements AttributeConverter<MeasureUnit, String> {
    @Override
    public String convertToDatabaseColumn(MeasureUnit attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public MeasureUnit convertToEntityAttribute(String dbData) {
        return dbData == null ? null : MeasureUnit.valueOf(dbData);
    }
}