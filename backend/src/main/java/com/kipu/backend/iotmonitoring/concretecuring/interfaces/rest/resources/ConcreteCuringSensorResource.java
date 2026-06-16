package com.kipu.backend.iotmonitoring.concretecuring.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Resource for a concrete curing sensor monitor response.
 */
@Schema(
        name = "ConcreteCuringSensorResponse",
        description = "Concrete curing sensor information response",
        example = "{\"id\": 1, \"projectId\": \"PRJ-2026-001\", \"sensorId\": \"SNS-DHT22-09\", \"state\": \"IN_PROGRESS\", \"location\": \"Sector B - Column 4\", \"temperature\": \"25.4 CELSIUS\", \"humidityPercentage\": 85, \"temperatureLimit\": 65.0}"
)
public record ConcreteCuringSensorResource(
        @Schema(description = "Concrete curing unique technical identifier", example = "1")
        Long id,

        @Schema(description = "Associated project identifier", example = "PRJ-2026-001")
        String projectId,

        @Schema(description = "Physical IoT hardware sensor identifier", example = "SNS-DHT22-09")
        String sensorId,

        @Schema(description = "Current readable state of the curing process", example = "IN_PROGRESS")
        String state,

        @Schema(description = "Specific location description within the job site", example = "Sector B - Column 4")
        String location,

        @Schema(description = "Formatted temperature reading with its unit", example = "25.4 CELSIUS")
        String temperature,

        @Schema(description = "Current humidity percentage level", example = "85")
        Integer humidityPercentage,

        @Schema(description = "Maximum safe temperature threshold configured", example = "65.0")
        Double temperatureLimit
) {
}