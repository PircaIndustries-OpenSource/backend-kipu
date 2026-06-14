package com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.domain.model.valueobjects.Name;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class NameAttributeConverter implements AttributeConverter<Name, String> {
    @Override
    public String convertToDatabaseColumn(Name attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public Name convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new Name(dbData);
    }
}