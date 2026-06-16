package com.kipu.backend.iotmonitoring.geolocalization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Resource for a geolocalization sensor.
 */
@Schema(
        name = "GeolocalizationSensorResponse",
        description = "Geolocalization sensor information response",
        example = "{\"id\": 1, \"projectId\": \"PROJ-2026-SURCO\", \"sensorId\": \"SNS-IOT-99X\", \"numberId\": 1, \"name\": \"Grúa Torre Alfa\", \"state\": 1, \"longitude\": -76.9874, \"latitude\": -12.1345}"
)
public record GeolocalizationSensorResource(
        @Schema(description = "Geolocalization sensor unique database identifier", example = "1")
        Long id,

        @Schema(description = "Associated project identifier code", example = "PROJ-2026-SURCO")
        String projectId,

        @Schema(description = "Physical IoT hardware sensor identifier", example = "SNS-IOT-99X")
        String sensorId,

        @Schema(description = "Internal sensor sequence number", example = "1")
        Integer numberId,

        @Schema(description = "Custom descriptive name assigned to the sensor", example = "Grúa Torre Alfa")
        String name,

        @Schema(description = "Current state integer representation (1, 2, 3)", example = "1")
        Integer state,

        @Schema(description = "Geographic longitude coordinate component", example = "-76.9874")
        Double longitude,

        @Schema(description = "Geographic latitude coordinate component", example = "-12.1345")
        Double latitude
) {
}