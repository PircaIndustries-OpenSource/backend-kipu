package com.kipu.backend.Logistics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Response resource representing a material waste record")
public record MaterialWasteResource(

        @Schema(description = "Persistent identifier", example = "1")
        Long id,

        @Schema(description = "Project identifier", example = "1")
        String projectId,

        @Schema(description = "Material catalog identifier", example = "5")
        Integer materialCatalogId,

        @Schema(description = "Wasted quantity", example = "50")
        Integer quantity,

        @Schema(description = "Unit of measure", example = "und")
        String unit,

        @Schema(description = "Waste classification type", example = "ROTURA")
        String classificationType,

        @Schema(description = "Date the waste occurred", example = "2026-05-02")
        LocalDate date,

        @Schema(description = "Description of the waste event")
        String description,

        @Schema(description = "DNI or name of the person who reported the waste", example = "40213390")
        String reportedBy,

        @Schema(description = "Optional photo URL")
        String photoUrl
) {}
