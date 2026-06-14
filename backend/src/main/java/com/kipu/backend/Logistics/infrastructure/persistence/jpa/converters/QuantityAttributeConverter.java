package com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.domain.model.valueobjects.Quantity;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class QuantityAttributeConverter implements AttributeConverter<Quantity, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Quantity attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public Quantity convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : new Quantity(dbData);
    }
}