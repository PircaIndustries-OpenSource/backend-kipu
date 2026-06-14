package com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.domain.model.valueobjects.external.BudgetLineId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BudgetLineIdAttributeConverter implements AttributeConverter<BudgetLineId, Integer> {
    @Override
    public Integer convertToDatabaseColumn(BudgetLineId attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public BudgetLineId convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : new BudgetLineId(dbData);
    }
}