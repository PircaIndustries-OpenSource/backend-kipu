package com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.domain.model.valueobjects.external.UserId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserIdAttributeConverter implements AttributeConverter<UserId, Integer> {
    @Override
    public Integer convertToDatabaseColumn(UserId attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public UserId convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : new UserId(dbData);
    }
}