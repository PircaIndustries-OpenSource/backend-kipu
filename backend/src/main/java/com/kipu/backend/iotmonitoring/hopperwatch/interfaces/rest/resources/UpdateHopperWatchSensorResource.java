package com.kipu.backend.iotmonitoring.hopperwatch.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Resource for updating an existing hopper watch monitor.
 */
@Schema(
        name = "UpdateHopperWatchSensorRequest",
        description = "Request payload for updating an existing hopper watch monitor configuration and telemetry limits",
        example = "{\"sensorId\": \"SN-HW-9942\", \"name\": \"Tolva de Agregados Principal Modificada\", \"unit\": \"kg\", \"state\": 1, \"lastLecture\": 150, \"limit\": 5000}"
)
public record UpdateHopperWatchSensorResource(
        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Unique hardware IoT sensor identifier", example = "SN-HW-9942", minLength = 1, maxLength = 50)
        String sensorId,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Descriptive name for the hopper monitor", example = "Tolva de Agregados Principal Modificada", minLength = 1, maxLength = 100)
        String name,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Measurement unit for the telemetry readings", example = "kg", minLength = 1, maxLength = 10)
        String unit,

        @NotNull(message = "{validation.not-null}")
        @Schema(description = "Numerical business value representing the operational state (1 = OK, 2 = ALERT, 3 = CRITICAL)", example = "1")
        Integer state,

        @NotNull(message = "{validation.not-null}")
        @PositiveOrZero(message = "{validation.positive-or-zero}")
        @Schema(description = "Current telemetry reading value as a whole number", example = "150")
        Integer lastLecture,

        @NotNull(message = "{validation.not-null}")
        @PositiveOrZero(message = "{validation.positive-or-zero}")
        @Schema(description = "Critical safety weight/volume limit threshold for alerts as a whole number", example = "5000")
        Integer limit
) {
}