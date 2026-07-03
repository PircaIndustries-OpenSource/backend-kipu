package com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.domain.model.valueobjects.WasteClassificationType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class WasteClassificationTypeAttributeConverter implements AttributeConverter<WasteClassificationType, String> {

    @Override
    public String convertToDatabaseColumn(WasteClassificationType attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public WasteClassificationType convertToEntityAttribute(String dbData) {
        return dbData == null ? null : WasteClassificationType.valueOf(dbData);
    }
}
