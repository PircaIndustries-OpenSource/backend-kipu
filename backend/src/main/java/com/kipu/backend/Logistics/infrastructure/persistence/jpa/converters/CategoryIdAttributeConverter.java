package com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.domain.model.valueobjects.CategoryId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoryIdAttributeConverter implements AttributeConverter<CategoryId, Integer> {
    @Override
    public Integer convertToDatabaseColumn(CategoryId attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public CategoryId convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : new CategoryId(dbData);
    }
}