package com.kipu.backend.Logistics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Request resource used to create a material catalog")
public record CreateMaterialCatalogResource(
        @Schema(description = "Material catalog name", example = "Cemento Portland", maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "material.catalog.error.name.notBlank")
        @Size(max = 100, message = "material.catalog.error.name.size")
        String name,

        @Schema(description = "Category identifier", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "material.catalog.error.categoryId.notBlank")
        @Positive(message = "material.catalog.error.categoryId.invalidValue")
        Integer categoryId,

        @Schema(description = "Measure unit", example = "BAG", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "material.catalog.error.measureUnit.notBlank")
        String measureUnit
) {}