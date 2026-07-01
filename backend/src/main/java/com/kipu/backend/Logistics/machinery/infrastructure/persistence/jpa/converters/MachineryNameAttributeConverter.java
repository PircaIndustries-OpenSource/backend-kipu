package com.kipu.backend.Logistics.machinery.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.machinery.domain.model.valueobjects.MachineryName;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MachineryNameAttributeConverter implements AttributeConverter<MachineryName, String> {

    @Override
    public String convertToDatabaseColumn(MachineryName attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public MachineryName convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new MachineryName(dbData);
    }
}
