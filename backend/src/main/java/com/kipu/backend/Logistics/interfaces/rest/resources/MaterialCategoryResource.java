package com.kipu.backend.Logistics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response resource representing a material category")
public record MaterialCategoryResource(
        @Schema(description = "Persistent identifier", example = "1")
        Long id,

        @Schema(description = "Material category name", example = "Agregados")
        String name,

        @Schema(description = "Category description", example = "Materiales pétreos y agregados")
        String description,

        @Schema(description = "Active status", example = "true")
        Boolean isActive
) {}