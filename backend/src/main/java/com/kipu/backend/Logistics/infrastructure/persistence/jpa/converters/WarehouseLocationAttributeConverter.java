package com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.domain.model.valueobjects.WarehouseLocation;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class WarehouseLocationAttributeConverter implements AttributeConverter<WarehouseLocation, String> {
    @Override
    public String convertToDatabaseColumn(WarehouseLocation attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public WarehouseLocation convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new WarehouseLocation(dbData);
    }
}