package com.kipu.backend.Logistics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response resource representing a material inventory")
public record MaterialInventoryResource(
        @Schema(description = "Persistent identifier", example = "1")
        Long id,

        @Schema(description = "Project identifier", example = "10")
        Integer projectId,

        @Schema(description = "Material catalog identifier", example = "5")
        Integer materialCatalogId,

        @Schema(description = "Current stock quantity", example = "100")
        Integer currentStock,

        @Schema(description = "Minimum stock quantity", example = "10")
        Integer minimumStock,

        @Schema(description = "Warehouse location", example = "A-12-5")
        String location
) {}