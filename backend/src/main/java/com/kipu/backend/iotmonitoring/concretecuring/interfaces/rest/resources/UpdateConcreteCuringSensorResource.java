package com.kipu.backend.iotmonitoring.concretecuring.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Resource for updating an existing concrete curing sensor.
 */

@Schema(
        name = "CreateConcreteCuringSensorResource",
        description = "Request payload for creating a new concrete curing sensor monitor",
        example = "{\"sensorId\": \"SNS-DHT22-09\", \"state\": 0, \"location\": \"Sector B - Column 4\", \"temperatureReading\": 25.4, \"temperatureUnit\": \"CELSIUS\", \"humidityPercentage\": 85, \"temperatureLimit\": 65.0}"
)
public record UpdateConcreteCuringSensorResource(
        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Physical IoT hardware sensor identifier", example = "SNS-DHT22-09", minLength = 1, maxLength = 50)
        String sensorId,

        @NotNull(message = "{validation.not-null}")
        @Schema(description = "Initial state integer code for the sensor", example = "0")
        Integer state,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Specific location description within the job site", example = "Sector B - Column 4", minLength = 1, maxLength = 100)
        String location,

        @NotNull(message = "{validation.not-null}")
        @Schema(description = "Initial temperature numeric reading", example = "25.4")
        Double temperatureReading,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Unit of measurement used for temperature", example = "CELSIUS", minLength = 1, maxLength = 10)
        String temperatureUnit,

        @NotNull(message = "{validation.not-null}")
        @Schema(description = "Initial humidity percentage level", example = "85")
        Integer humidityPercentage,

        @NotNull(message = "{validation.not-null}")
        @Schema(description = "Maximum safe temperature threshold configured", example = "65.0")
        Double temperatureLimit
) {
}
