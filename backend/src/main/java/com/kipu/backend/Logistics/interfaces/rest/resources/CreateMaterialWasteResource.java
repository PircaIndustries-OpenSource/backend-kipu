package com.kipu.backend.Logistics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "Request resource used to create a material waste record")
public record CreateMaterialWasteResource(

        @Schema(description = "Project identifier", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "material.waste.error.projectId.notBlank")
        @Positive(message = "material.waste.error.projectId.invalidValue")
        Integer projectId,

        @Schema(description = "Material catalog identifier (materialId)", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "material.waste.error.materialId.notBlank")
        @Positive(message = "material.waste.error.materialId.invalidValue")
        Integer materialCatalogId,

        @Schema(description = "Wasted quantity", example = "50", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "material.waste.error.quantity.notBlank")
        @PositiveOrZero(message = "material.waste.error.quantity.invalidValue")
        Integer quantity,

        @Schema(description = "Unit of measure", example = "und", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "material.waste.error.unit.notBlank")
        @Size(max = 50, message = "material.waste.error.unit.tooLong")
        String unit,

        @Schema(description = "Waste classification type (ROTURA, VENCIMIENTO, DEFECTO, CONTAMINACION)",
                example = "ROTURA", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "material.waste.error.classificationType.notBlank")
        String classificationType,

        @Schema(description = "Date the waste occurred (YYYY-MM-DD)", example = "2026-05-02",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "material.waste.error.date.notBlank")
        LocalDate date,

        @Schema(description = "Description of the waste event", example = "Ladrillos rotos durante maniobra de descarga.",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "material.waste.error.description.notBlank")
        @Size(max = 500, message = "material.waste.error.description.tooLong")
        String description,

        @Schema(description = "DNI or name of the person reporting the waste", example = "40213390",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "material.waste.error.reportedBy.notBlank")
        @Size(max = 100, message = "material.waste.error.reportedBy.tooLong")
        String reportedBy,

        @Schema(description = "Optional photo URL", example = "")
        String photoUrl
) {}
