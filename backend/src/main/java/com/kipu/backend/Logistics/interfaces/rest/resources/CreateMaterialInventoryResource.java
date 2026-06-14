package com.kipu.backend.Logistics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Request resource used to create a material inventory")
public record CreateMaterialInventoryResource(
        @Schema(description = "Project identifier", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "material.inventory.error.projectId.notBlank")
        @PositiveOrZero(message = "material.inventory.error.projectId.invalidValue")
        Integer projectId,

        @Schema(description = "Material catalog identifier", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "material.inventory.error.materialCatalogId.notBlank")
        @PositiveOrZero(message = "material.inventory.error.materialCatalogId.invalidValue")
        Integer materialCatalogId,

        @Schema(description = "Current stock quantity", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "material.inventory.error.currentStock.notBlank")
        @PositiveOrZero(message = "material.inventory.error.currentStock.invalidValue")
        Integer currentStock,

        @Schema(description = "Minimum stock quantity", example = "10")
        @PositiveOrZero(message = "material.inventory.error.minimumStock.invalidValue")
        Integer minimumStock,

        @Schema(description = "Warehouse location (format Aisle-Rack-Shelf)", example = "A-12-5", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "material.inventory.error.location.notBlank")
        @Pattern(regexp = "^[A-Za-z0-9]+-[A-Za-z0-9]+-[A-Za-z0-9]+$", message = "material.inventory.error.location.pattern")
        String location
) {}