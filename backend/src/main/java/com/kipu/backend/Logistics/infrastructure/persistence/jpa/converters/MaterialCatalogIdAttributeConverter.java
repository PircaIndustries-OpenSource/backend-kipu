package com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.domain.model.valueobjects.MaterialCatalogId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MaterialCatalogIdAttributeConverter implements AttributeConverter<MaterialCatalogId, Integer> {
    @Override
    public Integer convertToDatabaseColumn(MaterialCatalogId attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public MaterialCatalogId convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : new MaterialCatalogId(dbData);
    }
}