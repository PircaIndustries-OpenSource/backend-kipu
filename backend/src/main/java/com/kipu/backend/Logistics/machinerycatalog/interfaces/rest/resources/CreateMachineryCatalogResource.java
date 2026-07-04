package com.kipu.backend.Logistics.machinerycatalog.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "Request resource to create a new machinery catalog entry")
public record CreateMachineryCatalogResource(
        @Schema(description = "Machinery name", example = "Excavadora CAT 320", maxLength = 200)
        @NotBlank(message = "{machinerycatalog.validation.nameRequired}")
        @Size(max = 200, message = "{machinerycatalog.validation.nameSize}")
        String name,

        @Schema(description = "Brand of the machinery", example = "Caterpillar", maxLength = 100)
        @Size(max = 100, message = "{machinerycatalog.validation.brandSize}")
        String brand,

        @Schema(description = "Model of the machinery", example = "320D", maxLength = 100)
        @Size(max = 100, message = "{machinerycatalog.validation.modelSize}")
        String model,

        @Schema(description = "Serial number", example = "CAT-320D-2024-001", maxLength = 100)
        @Size(max = 100, message = "{machinerycatalog.validation.serialSize}")
        String serialNumber,

        @Schema(description = "Acquisition date", example = "2024-01-15")
        LocalDate acquisitionDate
) {}
