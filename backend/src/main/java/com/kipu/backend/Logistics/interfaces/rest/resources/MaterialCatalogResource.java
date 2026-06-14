package com.kipu.backend.Logistics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response resource representing a material catalog")
public record MaterialCatalogResource(
        @Schema(description = "Persistent identifier", example = "1")
        Long id,

        @Schema(description = "Material catalog name", example = "Cemento Portland")
        String name,

        @Schema(description = "Category identifier", example = "1")
        Integer categoryId,

        @Schema(description = "Measure unit", example = "BAG")
        String measureUnit
) {}