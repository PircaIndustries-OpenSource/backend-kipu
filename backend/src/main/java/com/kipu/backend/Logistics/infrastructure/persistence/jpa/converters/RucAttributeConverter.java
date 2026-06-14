package com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.domain.model.valueobjects.Ruc;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RucAttributeConverter implements AttributeConverter<Ruc, String> {
    @Override
    public String convertToDatabaseColumn(Ruc attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public Ruc convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new Ruc(dbData);
    }
}