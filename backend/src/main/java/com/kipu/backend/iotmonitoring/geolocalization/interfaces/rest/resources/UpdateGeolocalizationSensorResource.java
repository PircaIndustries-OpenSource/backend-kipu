package com.kipu.backend.iotmonitoring.geolocalization.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateGeolocalizationSensorResource(
        @NotBlank String sensorId,
        @NotNull Integer numberId,
        @NotBlank String name,
        @NotNull Integer state,
        @NotNull Double longitude,
        @NotNull Double latitude
) {
}
