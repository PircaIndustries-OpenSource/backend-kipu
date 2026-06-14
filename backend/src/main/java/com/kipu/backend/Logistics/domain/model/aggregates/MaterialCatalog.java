package com.kipu.backend.Logistics.domain.model.aggregates;

import com.kipu.backend.Logistics.domain.model.valueobjects.*;

import java.time.Instant;
import java.util.Objects;

public class MaterialCatalog {

    private final Long id;
    private final Name name;
    private final CategoryId categoryId;
    private final MeasureUnit measureUnit;
    private final Instant createdAt;
    private final Instant updatedAt;

    private MaterialCatalog(Long id, Name name, CategoryId categoryId,
                            MeasureUnit measureUnit, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "material.catalog.error.name.notBlank");
        this.categoryId = Objects.requireNonNull(categoryId, "material.catalog.error.categoryId.notBlank");
        this.measureUnit = Objects.requireNonNull(measureUnit, "material.catalog.error.measureUnit.notBlank");
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static MaterialCatalog create(Name name, CategoryId categoryId, MeasureUnit measureUnit) {
        Instant now = Instant.now();
        return new MaterialCatalog(null, name, categoryId, measureUnit, now, now);
    }

    public static MaterialCatalog rehydrate(Long id, Name name, CategoryId categoryId,
                                            MeasureUnit measureUnit, Instant createdAt, Instant updatedAt) {
        return new MaterialCatalog(id, name, categoryId, measureUnit, createdAt, updatedAt);
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public CategoryId getCategoryId() {
        return categoryId;
    }

    public MeasureUnit getMeasureUnit() {
        return measureUnit;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}