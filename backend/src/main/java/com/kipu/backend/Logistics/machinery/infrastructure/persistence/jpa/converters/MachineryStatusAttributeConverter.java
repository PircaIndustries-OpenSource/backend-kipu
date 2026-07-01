package com.kipu.backend.Logistics.machinery.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.machinery.domain.model.valueobjects.MachineryStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MachineryStatusAttributeConverter implements AttributeConverter<MachineryStatus, String> {

    @Override
    public String convertToDatabaseColumn(MachineryStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public MachineryStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : MachineryStatus.valueOf(dbData);
    }
}
