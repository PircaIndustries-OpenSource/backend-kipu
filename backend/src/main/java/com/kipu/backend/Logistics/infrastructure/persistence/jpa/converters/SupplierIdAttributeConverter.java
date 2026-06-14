package com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.domain.model.valueobjects.SupplierId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SupplierIdAttributeConverter implements AttributeConverter<SupplierId, Integer> {
    @Override
    public Integer convertToDatabaseColumn(SupplierId attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public SupplierId convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : new SupplierId(dbData);
    }
}