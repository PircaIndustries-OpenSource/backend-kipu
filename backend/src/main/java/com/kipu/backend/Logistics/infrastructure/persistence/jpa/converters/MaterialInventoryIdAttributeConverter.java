package com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.domain.model.valueobjects.MaterialInventoryId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MaterialInventoryIdAttributeConverter implements AttributeConverter<MaterialInventoryId, Integer> {
    @Override
    public Integer convertToDatabaseColumn(MaterialInventoryId attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public MaterialInventoryId convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : new MaterialInventoryId(dbData);
    }
}