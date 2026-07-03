package com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.domain.model.valueobjects.WasteUnit;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class WasteUnitAttributeConverter implements AttributeConverter<WasteUnit, String> {

    @Override
    public String convertToDatabaseColumn(WasteUnit attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public WasteUnit convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new WasteUnit(dbData);
    }
}
