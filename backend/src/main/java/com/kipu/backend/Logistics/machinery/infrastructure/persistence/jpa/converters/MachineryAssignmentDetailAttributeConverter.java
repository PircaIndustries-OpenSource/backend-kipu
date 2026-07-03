package com.kipu.backend.Logistics.machinery.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.machinery.domain.model.valueobjects.MachineryAssignmentDetail;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MachineryAssignmentDetailAttributeConverter implements AttributeConverter<MachineryAssignmentDetail, String> {

    @Override
    public String convertToDatabaseColumn(MachineryAssignmentDetail attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public MachineryAssignmentDetail convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new MachineryAssignmentDetail(dbData);
    }
}
