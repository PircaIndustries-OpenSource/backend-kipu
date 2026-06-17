package com.kipu.backend.iotmonitoring.hopperwatch.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Resource for a hopper watch monitor.
 */
@Schema(
        name = "HopperWatchSensorResponse",
        description = "Hopper watch information response payload",
        example = "{\"id\": 1, \"projectId\": \"PROJ-102\", \"sensorId\": \"SN-HW-9942\", \"name\": \"Tolva de Agregados Principal\", \"unit\": \"kg\", \"state\": 1, \"lastLecture\": 1250, \"safetyLimit\": 4500}"
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

        @Schema(description = "Current operational state of the hopper (1 = OK, 2 = ALERT, 3 = CRITICAL)", example = "1")
        Integer state,

        @Schema(description = "Last recorded telemetry measurement lecture as a whole number", example = "1250")
        Integer lastLecture,

        @Schema(description = "Critical safety limit threshold for weight/volume as a whole number", example = "4500")
        Integer safetyLimit
) {
}