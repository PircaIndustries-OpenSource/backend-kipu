package com.kipu.backend.Logistics.application.commands;

import com.kipu.backend.Logistics.domain.model.valueobjects.CategoryId;
import com.kipu.backend.Logistics.domain.model.valueobjects.MeasureUnit;
import com.kipu.backend.Logistics.domain.model.valueobjects.Name;

/**
 * Command for creating a material catalog.
 *
 * @param name        the name value object
 * @param categoryId  the category ID value object
 * @param measureUnit the measure unit enum
 */
public record CreateMaterialCatalogCommand(Name name, CategoryId categoryId, MeasureUnit measureUnit) {
    /**
     * Validates the command.
     *
     * @throws IllegalArgumentException if any parameter is null
     */
    public CreateMaterialCatalogCommand {
        if (name == null)
            throw new IllegalArgumentException("name cannot be null");
        if (categoryId == null)
            throw new IllegalArgumentException("categoryId cannot be null");
        if (measureUnit == null)
            throw new IllegalArgumentException("measureUnit cannot be null");
    }
}