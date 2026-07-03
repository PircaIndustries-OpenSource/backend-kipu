package com.kipu.backend.Logistics.machinery.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.machinery.domain.model.valueobjects.MachineryMaintenanceHours;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MachineryMaintenanceHoursAttributeConverter implements AttributeConverter<MachineryMaintenanceHours, String> {

    @Override
    public String convertToDatabaseColumn(MachineryMaintenanceHours attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public MachineryMaintenanceHours convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new MachineryMaintenanceHours(dbData);
    }
}
