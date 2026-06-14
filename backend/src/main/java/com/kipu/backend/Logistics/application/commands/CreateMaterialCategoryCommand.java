package com.kipu.backend.Logistics.application.commands;

import com.kipu.backend.Logistics.domain.model.valueobjects.Name;

/**
 * Command for creating a material category.
 *
 * @param name        the name value object
 * @param description the description (can be null or empty)
 * @param isActive    active flag (default true if null)
 */
public record CreateMaterialCategoryCommand(Name name, String description, Boolean isActive) {
    /**
     * Validates the command.
     *
     * @throws IllegalArgumentException if name is null
     */
    public CreateMaterialCategoryCommand {
        if (name == null)
            throw new IllegalArgumentException("name cannot be null");
        // description can be null, isActive can be null
    }
}