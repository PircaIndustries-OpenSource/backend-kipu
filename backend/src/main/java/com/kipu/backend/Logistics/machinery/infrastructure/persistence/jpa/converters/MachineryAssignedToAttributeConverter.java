package com.kipu.backend.Logistics.machinery.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.machinery.domain.model.valueobjects.MachineryAssignedTo;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MachineryAssignedToAttributeConverter implements AttributeConverter<MachineryAssignedTo, String> {

    @Override
    public String convertToDatabaseColumn(MachineryAssignedTo attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public MachineryAssignedTo convertToEntityAttribute(String dbData) {
        return dbData == null ? new MachineryAssignedTo(null) : new MachineryAssignedTo(dbData);
    }
}
