package com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.domain.model.valueobjects.Phone;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PhoneAttributeConverter implements AttributeConverter<Phone, String> {
    @Override
    public String convertToDatabaseColumn(Phone attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public Phone convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new Phone(dbData);
    }
}