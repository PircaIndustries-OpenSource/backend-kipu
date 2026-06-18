package com.kipu.backend.iotmonitoring.hopperwatch.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateHopperWatchSensorResource(
        @NotBlank String sensorId,
        @NotBlank String name,
        @NotBlank String unit,
        @NotNull Integer state,
        @NotNull @PositiveOrZero Integer lastLecture,
        @NotNull @PositiveOrZero Integer limit
) {
}
