package com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities;

import com.kipu.backend.Logistics.domain.model.valueobjects.MaterialCatalogId;
import com.kipu.backend.Logistics.domain.model.valueobjects.Quantity;
import com.kipu.backend.Logistics.domain.model.valueobjects.WasteClassificationType;
import com.kipu.backend.Logistics.domain.model.valueobjects.WasteUnit;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.ProjectId;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.MaterialCatalogIdAttributeConverter;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.ProjectIdAttributeConverter;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.QuantityAttributeConverter;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.WasteClassificationTypeAttributeConverter;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.WasteUnitAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "material_waste")
public class MaterialWasteJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id", nullable = false)
    @Convert(converter = ProjectIdAttributeConverter.class)
    private ProjectId projectId;

    @Column(name = "material_catalog_id", nullable = false)
    @Convert(converter = MaterialCatalogIdAttributeConverter.class)
    private MaterialCatalogId materialCatalogId;

    @Column(name = "quantity", nullable = false)
    @Convert(converter = QuantityAttributeConverter.class)
    private Quantity quantity;

    @Column(name = "unit", nullable = false, length = 50)
    @Convert(converter = WasteUnitAttributeConverter.class)
    private WasteUnit unit;

    @Column(name = "classification_type", nullable = false, length = 50)
    @Convert(converter = WasteClassificationTypeAttributeConverter.class)
    private WasteClassificationType classificationType;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "reported_by", nullable = false, length = 100)
    private String reportedBy;

    @Column(name = "photo_url", length = 500)
    private String photoUrl;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    public MaterialWasteJpaEntity(Long id, ProjectId projectId, MaterialCatalogId materialCatalogId,
                                   Quantity quantity, WasteUnit unit,
                                   WasteClassificationType classificationType,
                                   LocalDate date, String description,
                                   String reportedBy, String photoUrl,
                                   Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.projectId = projectId;
        this.materialCatalogId = materialCatalogId;
        this.quantity = quantity;
        this.unit = unit;
        this.classificationType = classificationType;
        this.date = date;
        this.description = description;
        this.reportedBy = reportedBy;
        this.photoUrl = photoUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
