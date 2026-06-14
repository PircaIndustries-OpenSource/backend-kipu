package com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.domain.model.valueobjects.RequestPriority;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RequestPriorityAttributeConverter implements AttributeConverter<RequestPriority, String> {
    @Override
    public String convertToDatabaseColumn(RequestPriority attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public RequestPriority convertToEntityAttribute(String dbData) {
        return dbData == null ? null : RequestPriority.valueOf(dbData);
    }
}