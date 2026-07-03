package com.kipu.backend.Logistics.application.commands;

import com.kipu.backend.Logistics.domain.model.valueobjects.MaterialCatalogId;
import com.kipu.backend.Logistics.domain.model.valueobjects.Quantity;
import com.kipu.backend.Logistics.domain.model.valueobjects.WasteClassificationType;
import com.kipu.backend.Logistics.domain.model.valueobjects.WasteUnit;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.ProjectId;

import java.time.LocalDate;

/**
 * Command for creating a material waste record.
 *
 * @param projectId          the project ID value object
 * @param materialCatalogId  the material catalog ID value object
 * @param quantity           the wasted quantity
 * @param unit               the unit of measure
 * @param classificationType the waste classification type
 * @param date               the date the waste occurred
 * @param description        description of the waste event
 * @param reportedBy         identifier (DNI or name) of the person reporting the waste
 * @param photoUrl           optional photo URL (may be null or empty)
 */
public record CreateMaterialWasteCommand(
        ProjectId projectId,
        MaterialCatalogId materialCatalogId,
        Quantity quantity,
        WasteUnit unit,
        WasteClassificationType classificationType,
        LocalDate date,
        String description,
        String reportedBy,
        String photoUrl
) {
    public CreateMaterialWasteCommand {
        if (projectId == null) throw new IllegalArgumentException("projectId cannot be null");
        if (materialCatalogId == null) throw new IllegalArgumentException("materialCatalogId cannot be null");
        if (quantity == null) throw new IllegalArgumentException("quantity cannot be null");
        if (unit == null) throw new IllegalArgumentException("unit cannot be null");
        if (classificationType == null) throw new IllegalArgumentException("classificationType cannot be null");
        if (date == null) throw new IllegalArgumentException("date cannot be null");
        if (description == null || description.isBlank()) throw new IllegalArgumentException("description cannot be blank");
        if (reportedBy == null || reportedBy.isBlank()) throw new IllegalArgumentException("reportedBy cannot be blank");
    }
}
