package com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.domain.model.valueobjects.RequestId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RequestIdAttributeConverter implements AttributeConverter<RequestId, Integer> {
    @Override
    public Integer convertToDatabaseColumn(RequestId attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public RequestId convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : new RequestId(dbData);
    }
}