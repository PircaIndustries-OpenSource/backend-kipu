package com.kipu.backend.iotmonitoring.seismiccontrol.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Resource for a seismic control sensor response.
 * <p>Represents the clean outbound payload structure returned by the public REST API endpoints.</p>
 */
@Schema(
        name = "SeismicControlSensorResponse",
        description = "Seismic control sensor information response",
        example = "{\"id\": 1, \"projectId\": \"PRJ-001\", \"sensorId\": \"SNS-SEISMIC-04\", \"unit\": \"mm/s\", \"lastLecture\": 1.25, \"limit\": 4.50, \"location\": \"Basement Pillar A\", \"timeLecture\": \"2026-06-17T09:00:00Z\", \"state\": 1}"
)
public record SeismicControlSensorResource(
        @Schema(description = "Seismic control sensor internal database identifier", example = "1")
        Long id,

        @Schema(description = "Associated architectural project identifier", example = "PRJ-001")
        String projectId,

        @Schema(description = "Physical hardware identification code", example = "SNS-SEISMIC-04")
        String sensorId,

        @Schema(description = "Measurement metric unit", example = "mm/s")
        String unit,

        @Schema(description = "Last recorded seismic telemetry value", example = "1.25")
        Double lastLecture,

        @Schema(description = "Safety threshold numeric limit", example = "4.50")
        Double limit,

        @Schema(description = "Physical installation location description", example = "Basement Pillar A")
        String location,

        @Schema(description = "Timestamp text of the measurement lecture", example = "2026-06-17T09:00:00Z")
        String timeLecture,

        @Schema(description = "Status integer code of the sensor", example = "1")
        Integer state
) {
}