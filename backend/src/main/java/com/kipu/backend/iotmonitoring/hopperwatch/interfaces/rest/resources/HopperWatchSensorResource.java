package com.kipu.backend.iotmonitoring.hopperwatch.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Resource for a hopper watch monitor.
 */
@Schema(
        name = "HopperWatchSensorResponse",
        description = "Hopper watch information response payload",
        example = "{\"id\": 1, \"projectId\": \"PROJ-102\", \"sensorId\": \"SN-HW-9942\", \"name\": \"Tolva de Agregados Principal\", \"unit\": \"kg\", \"state\": \"OK\", \"lastLecture\": 1250.5, \"safetyLimit\": 4500.0}"
)
public record HopperWatchSensorResource(
        @Schema(description = "Hopper watch unique identifier", example = "1")
        Long id,

        @Schema(description = "Project identifier associated with the hopper", example = "PROJ-102")
        String projectId,

        @Schema(description = "Unique hardware IoT sensor identifier", example = "SN-HW-9942")
        String sensorId,

        @Schema(description = "Descriptive name of the hopper monitor", example = "Tolva de Agregados Principal")
        String name,

        @Schema(description = "Measurement unit for telemetry readings", example = "kg")
        String unit,

        @Schema(description = "Current operational state of the hopper", example = "OK")
        String state,

        @Schema(description = "Last recorded telemetry measurement lecture", example = "1250.5")
        Double lastLecture,

        @Schema(description = "Critical safety limit threshold for weight/volume", example = "4500.0")
        Double safetyLimit
) {
}