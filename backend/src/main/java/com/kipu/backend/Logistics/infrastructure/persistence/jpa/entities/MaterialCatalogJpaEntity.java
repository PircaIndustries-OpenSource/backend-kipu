package com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities;

import com.kipu.backend.Logistics.domain.model.valueobjects.CategoryId;
import com.kipu.backend.Logistics.domain.model.valueobjects.MeasureUnit;
import com.kipu.backend.Logistics.domain.model.valueobjects.Name;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.CategoryIdAttributeConverter;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.MeasureUnitAttributeConverter;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.NameAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "material_catalog",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name", name = "uk_material_catalog_name")
        })
public class MaterialCatalogJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    @Convert(converter = NameAttributeConverter.class)
    private Name name;

    @Column(name = "category_id", nullable = false)
    @Convert(converter = CategoryIdAttributeConverter.class)
    private CategoryId categoryId;

    @Column(name = "measure_unit", nullable = false)
    @Convert(converter = MeasureUnitAttributeConverter.class)
    private MeasureUnit measureUnit;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    public MaterialCatalogJpaEntity(Long id, Name name, CategoryId categoryId,
                                    MeasureUnit measureUnit, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.measureUnit = measureUnit;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}