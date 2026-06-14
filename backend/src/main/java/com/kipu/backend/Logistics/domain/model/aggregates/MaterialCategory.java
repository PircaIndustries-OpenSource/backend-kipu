package com.kipu.backend.Logistics.domain.model.aggregates;

import com.kipu.backend.Logistics.domain.model.valueobjects.Name;

import java.time.Instant;
import java.util.Objects;

public class MaterialCategory {

    private final Long id;
    private final Name name;
    private final String description;
    private final Boolean isActive;
    private final Instant createdAt;
    private final Instant updatedAt;

    private MaterialCategory(Long id, Name name, String description,
                             Boolean isActive, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "material.category.error.name.notBlank");
        this.description = description != null ? description : "";
        this.isActive = isActive != null ? isActive : true;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static MaterialCategory create(Name name, String description, Boolean isActive) {
        Instant now = Instant.now();
        return new MaterialCategory(null, name, description, isActive, now, now);
    }

    public static MaterialCategory rehydrate(Long id, Name name, String description,
                                             Boolean isActive, Instant createdAt, Instant updatedAt) {
        return new MaterialCategory(id, name, description, isActive, createdAt, updatedAt);
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}