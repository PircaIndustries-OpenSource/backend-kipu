package com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters;

import com.kipu.backend.Logistics.domain.model.valueobjects.external.ProjectId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ProjectIdAttributeConverter implements AttributeConverter<ProjectId, String> {
    @Override
    public String convertToDatabaseColumn(ProjectId attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public ProjectId convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new ProjectId(dbData);
    }
}