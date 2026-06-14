package com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.domain.model.valueobjects.RequestStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RequestStatusAttributeConverter implements AttributeConverter<RequestStatus, String> {
    @Override
    public String convertToDatabaseColumn(RequestStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public RequestStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : RequestStatus.valueOf(dbData);
    }
}