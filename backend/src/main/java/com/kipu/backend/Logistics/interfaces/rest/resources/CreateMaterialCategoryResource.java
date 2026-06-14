package com.kipu.backend.Logistics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request resource used to create a material category")
public record CreateMaterialCategoryResource(
        @Schema(description = "Material category name", example = "Agregados", maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "material.category.error.name.notBlank")
        @Size(max = 100, message = "material.category.error.name.size")
        String name,

        @Schema(description = "Category description", example = "Materiales pétreos y agregados", maxLength = 255)
        @Size(max = 255, message = "material.category.error.description.size")
        String description,

        @Schema(description = "Active status", example = "true", defaultValue = "true")
        Boolean isActive
) {}