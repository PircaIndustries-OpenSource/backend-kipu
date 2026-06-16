package com.kipu.backend.iotmonitoring.geolocalization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Resource for creating a geolocalization sensor.
 */
@Schema(
        name = "CreateGeolocalizationSensorResource",
        description = "Request payload for creating a new geolocalization sensor for IoT monitoring",
        example = "{\"projectId\": \"PROJ-2026-SURCO\", \"sensorId\": \"SNS-IOT-99X\", \"numberId\": 1, \"name\": \"Grúa Torre Alfa\", \"state\": 1, \"longitude\": -76.9874, \"latitude\": -12.1345}"
)
public record CreateGeolocalizationSensorResource(
        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Associated project unique code identifier", example = "PROJ-2026-SURCO", minLength = 1, maxLength = 50)
        String projectId,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Physical IoT hardware sensor identifier", example = "SNS-IOT-99X", minLength = 1, maxLength = 50)
        String sensorId,

        @NotNull(message = "{validation.not-null}")
        @Min(value = 1, message = "The internal sensor sequence number must be greater than 0")
        @Schema(description = "Internal sequential sequence number assigned to the sensor", example = "1")
        Integer numberId,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Descriptive custom name assigned to the sensor", example = "Grúa Torre Alfa", minLength = 1, maxLength = 100)
        String name,

        @NotNull(message = "{validation.not-null}")
        @Min(value = 1, message = "State must be a valid option (minimum 1)")
        @Max(value = 3, message = "State must be a valid option (maximum 3)")
        @Schema(description = "Initial state value numerical representation", example = "1")
        Integer state,

        @NotNull(message = "{validation.not-null}")
        @Min(value = -180, message = "Longitude must be a valid geographic coordinate between -180 and 180")
        @Max(value = 180, message = "Longitude must be a valid geographic coordinate between -180 and 180")
        @Schema(description = "Initial geographic longitude coordinate component", example = "-76.9874")
        Double longitude,

        @NotNull(message = "{validation.not-null}")
        @Min(value = -90, message = "Latitude must be a valid geographic coordinate between -90 and 90")
        @Max(value = 90, message = "Latitude must be a valid geographic coordinate between -90 and 90")
        @Schema(description = "Initial geographic latitude coordinate component", example = "-12.1345")
        Double latitude
) {
}