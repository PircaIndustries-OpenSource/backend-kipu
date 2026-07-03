package com.kipu.backend.Logistics.domain.model.aggregates;

import com.kipu.backend.Logistics.domain.model.valueobjects.MaterialCatalogId;
import com.kipu.backend.Logistics.domain.model.valueobjects.Quantity;
import com.kipu.backend.Logistics.domain.model.valueobjects.WasteClassificationType;
import com.kipu.backend.Logistics.domain.model.valueobjects.WasteUnit;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.ProjectId;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Aggregate root representing a material waste report.
 * Captures the loss or unusable status of a material on a construction project.
 */
public class MaterialWaste {

    private final Long id;
    private final ProjectId projectId;
    private final MaterialCatalogId materialCatalogId;
    private final Quantity quantity;
    private final WasteUnit unit;
    private final WasteClassificationType classificationType;
    private final LocalDate date;
    private final String description;
    private final String reportedBy;
    private final String photoUrl;
    private final Instant createdAt;
    private final Instant updatedAt;

    private MaterialWaste(Long id, ProjectId projectId, MaterialCatalogId materialCatalogId,
                          Quantity quantity, WasteUnit unit, WasteClassificationType classificationType,
                          LocalDate date, String description, String reportedBy, String photoUrl,
                          Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.projectId = Objects.requireNonNull(projectId, "material.waste.error.projectId.notBlank");
        this.materialCatalogId = Objects.requireNonNull(materialCatalogId, "material.waste.error.materialId.notBlank");
        this.quantity = Objects.requireNonNull(quantity, "material.waste.error.quantity.notBlank");
        this.unit = Objects.requireNonNull(unit, "material.waste.error.unit.notBlank");
        this.classificationType = Objects.requireNonNull(classificationType, "material.waste.error.classificationType.notBlank");
        this.date = Objects.requireNonNull(date, "material.waste.error.date.notBlank");
        this.description = Objects.requireNonNull(description, "material.waste.error.description.notBlank");
        this.reportedBy = Objects.requireNonNull(reportedBy, "material.waste.error.reportedBy.notBlank");
        this.photoUrl = photoUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ── Factory Methods ──────────────────────────────────────────────────────────

    public static MaterialWaste create(ProjectId projectId, MaterialCatalogId materialCatalogId,
                                       Quantity quantity, WasteUnit unit,
                                       WasteClassificationType classificationType,
                                       LocalDate date, String description,
                                       String reportedBy, String photoUrl) {
        Instant now = Instant.now();
        return new MaterialWaste(null, projectId, materialCatalogId, quantity, unit,
                classificationType, date, description, reportedBy, photoUrl, now, now);
    }

    public static MaterialWaste rehydrate(Long id, ProjectId projectId, MaterialCatalogId materialCatalogId,
                                          Quantity quantity, WasteUnit unit,
                                          WasteClassificationType classificationType,
                                          LocalDate date, String description,
                                          String reportedBy, String photoUrl,
                                          Instant createdAt, Instant updatedAt) {
        return new MaterialWaste(id, projectId, materialCatalogId, quantity, unit,
                classificationType, date, description, reportedBy, photoUrl, createdAt, updatedAt);
    }

    // ── Getters ──────────────────────────────────────────────────────────────────

    public Long getId() { return id; }

    public ProjectId getProjectId() { return projectId; }

    public MaterialCatalogId getMaterialCatalogId() { return materialCatalogId; }

    public Quantity getQuantity() { return quantity; }

    public WasteUnit getUnit() { return unit; }

    public WasteClassificationType getClassificationType() { return classificationType; }

    public LocalDate getDate() { return date; }

    public String getDescription() { return description; }

    public String getReportedBy() { return reportedBy; }

    public String getPhotoUrl() { return photoUrl; }

    public Instant getCreatedAt() { return createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
}
