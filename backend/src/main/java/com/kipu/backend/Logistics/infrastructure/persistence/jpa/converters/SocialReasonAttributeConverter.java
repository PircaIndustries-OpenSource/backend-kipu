package com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.domain.model.valueobjects.SocialReason;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SocialReasonAttributeConverter implements AttributeConverter<SocialReason, String> {
    @Override
    public String convertToDatabaseColumn(SocialReason attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public SocialReason convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new SocialReason(dbData);
    }
}