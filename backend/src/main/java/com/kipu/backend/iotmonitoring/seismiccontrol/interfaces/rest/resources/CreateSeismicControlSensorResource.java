package com.kipu.backend.iotmonitoring.seismiccontrol.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Resource for creating a seismic control sensor.
 * <p>Represents the strict incoming payload format for public REST API endpoints,
 * fully documented for Swagger UI visualization.</p>
 */
@Schema(
        name = "CreateSeismicControlSensorRequest",
        description = "Request payload for creating a new seismic control sensor node",
        example = "{\"projectId\": \"PRJ-001\", \"sensorId\": \"SNS-SEISMIC-04\", \"unit\": \"mm/s\", \"lastLecture\": 1.25, \"limit\": 4.50, \"location\": \"Basement Pillar A\", \"timeLecture\": \"2026-06-17T09:00:00Z\", \"state\": 1}"
)
public record CreateSeismicControlSensorResource(
        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Associated architectural project identifier", example = "PRJ-001", minLength = 1, maxLength = 36)
        String projectId,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Physical hardware identification code", example = "SNS-SEISMIC-04", minLength = 1, maxLength = 50)
        String sensorId,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Measurement metric unit", example = "mm/s", minLength = 1, maxLength = 10)
        String unit,

        @NotNull(message = "{validation.not-null}")
        @Schema(description = "Last recorded seismic telemetry value", example = "1.25")
        Double lastLecture,

        @NotNull(message = "{validation.not-null}")
        @Schema(description = "Safety threshold numeric limit", example = "4.50")
        Double limit,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Physical installation location description", example = "Basement Pillar A", minLength = 1, maxLength = 150)
        String location,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Timestamp text of the measurement lecture", example = "2026-06-17T09:00:00Z")
        String timeLecture,

        @NotNull(message = "{validation.not-null}")
        @Schema(description = "Status integer code of the sensor", example = "1")
        Integer state
) {
}