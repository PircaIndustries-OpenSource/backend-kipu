package com.kipu.backend.Logistics.machinerycatalog.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Response resource representing a machinery catalog entry")
public record MachineryCatalogResource(
        @Schema(description = "Persistent identifier", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
        String id,

        @Schema(description = "Machinery name", example = "Excavadora CAT 320")
        String name,

        @Schema(description = "Brand", example = "Caterpillar")
        String brand,

        @Schema(description = "Model", example = "320D")
        String model,

        @Schema(description = "Serial number", example = "CAT-320D-2024-001")
        String serialNumber,

        @Schema(description = "Acquisition date", example = "2024-01-15")
        LocalDate acquisitionDate
) {}
