package com.kipu.backend.iotmonitoring.seismiccontrol.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateSeismicControlSensorResource(
        @NotBlank String sensorId,
        @NotBlank String unit,
        @NotNull @PositiveOrZero Double lastLecture,
        @NotNull @PositiveOrZero Double limit,
        @NotBlank String location,
        @NotBlank String timeLecture,
        @NotNull Integer state) {
}
