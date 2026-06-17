package com.kipu.backend.iotmonitoring.hopperwatch.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Resource for creating a hopper watch monitor.
 */
@Schema(
        name = "CreateHopperWatchSensorRequest",
        description = "Request payload for creating a new hopper watch monitor",
        example = "{\"projectId\": \"PROJ-102\", \"sensorId\": \"SN-HW-9942\", \"name\": \"Tolva de Agregados Principal\", \"unit\": \"kg\", \"lastLecture\": 0.0, \"safetyLimit\": 4500.0}"
)
public record CreateHopperWatchSensorResource(
        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Project identifier associated with the hopper", example = "PROJ-102", minLength = 1, maxLength = 36)
        String projectId,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Unique hardware IoT sensor identifier", example = "SN-HW-9942", minLength = 1, maxLength = 50)
        String sensorId,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Descriptive name for the hopper monitor", example = "Tolva de Agregados Principal", minLength = 1, maxLength = 100)
        String name,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Measurement unit for the telemetry readings", example = "kg", minLength = 1, maxLength = 10)
        String unit,

        @NotNull(message = "{validation.not-null}")
        @PositiveOrZero(message = "{validation.positive-or-zero}")
        @Schema(description = "Initial telemetry reading value", example = "0.0")
        Double lastLecture,

        @NotNull(message = "{validation.not-null}")
        @PositiveOrZero(message = "{validation.positive-or-zero}")
        @Schema(description = "Critical safety weight/volume limit threshold for alerts", example = "4500.0")
        Double safetyLimit
) {
}