package com.kipu.backend.iotmonitoring.geolocalization.interfaces.rest;

import com.kipu.backend.iotmonitoring.geolocalization.application.commandservices.GeolocalizationSensorCommandService;
import com.kipu.backend.iotmonitoring.geolocalization.application.queryservices.GeolocalizationSensorQueryService;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.queries.GetAllGeolocalizationSensorsQuery;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.queries.GetGeolocalizationSensorByIdQuery;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.queries.GetGeolocalizationSensorsByProjectIdQuery;
import com.kipu.backend.iotmonitoring.geolocalization.interfaces.rest.resources.CreateGeolocalizationSensorResource;
import com.kipu.backend.iotmonitoring.geolocalization.interfaces.rest.resources.GeolocalizationSensorResource;
import com.kipu.backend.iotmonitoring.geolocalization.interfaces.rest.transform.CreateGeolocalizationSensorCommandFromResourceAssembler;
import com.kipu.backend.iotmonitoring.geolocalization.interfaces.rest.transform.GeolocalizationSensorResourceFromEntityAssembler;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.commands.DeleteGeolocalizationSensorCommand;
import com.kipu.backend.iotmonitoring.geolocalization.interfaces.rest.resources.UpdateGeolocalizationSensorResource;
import com.kipu.backend.iotmonitoring.geolocalization.interfaces.rest.transform.UpdateGeolocalizationSensorCommandFromResourceAssembler;
import com.kipu.backend.shared.application.result.ApplicationError;
import com.kipu.backend.shared.application.result.Result;
import com.kipu.backend.shared.interfaces.rest.transform.ErrorResponseAssembler;
import com.kipu.backend.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * REST controller that exposes geolocalization sensor resources and retrieval endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/geolocalization-sensors", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Geolocalization Sensors", description = "Geolocalization sensors management endpoints")
public class GeolocalizationSensorsController {
    private final GeolocalizationSensorCommandService geolocalizationSensorCommandService;
    private final GeolocalizationSensorQueryService geolocalizationSensorQueryService;

    /**
     * Constructor
     * @param geolocalizationSensorCommandService The {@link GeolocalizationSensorCommandService} instance
     * @param geolocalizationSensorQueryService The {@link GeolocalizationSensorQueryService} instance
     */
    public GeolocalizationSensorsController(
            GeolocalizationSensorCommandService geolocalizationSensorCommandService,
            GeolocalizationSensorQueryService geolocalizationSensorQueryService) {
        this.geolocalizationSensorCommandService = geolocalizationSensorCommandService;
        this.geolocalizationSensorQueryService = geolocalizationSensorQueryService;
    }

    /**
     * Create a new geolocalization sensor
     * @param resource The {@link CreateGeolocalizationSensorResource} instance
     * @return A {@link GeolocalizationSensorResource} resource for the created sensor
     */
    @PostMapping
    @Operation(
            summary = "Create a new geolocalization sensor",
            description = "Creates a new geolocalization sensor for IoT monitoring with physical sensor and coordinate information."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Geolocalization sensor created successfully",
                    content = @Content(schema = @Schema(implementation = GeolocalizationSensorResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Conflict - geolocalization sensor already exists")
    })
    public ResponseEntity<?> createGeolocalizationSensor(@Valid @RequestBody CreateGeolocalizationSensorResource resource) {
        var createGeolocalizationSensorCommand = CreateGeolocalizationSensorCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = geolocalizationSensorCommandService.handle(createGeolocalizationSensorCommand);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                GeolocalizationSensorResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    /**
     * Get a geolocalization sensor by ID
     * @param geolocalizationSensorId The geolocalization sensor database ID
     * @return A {@link GeolocalizationSensorResource} resource for the sensor
     */
    @GetMapping("/{geolocalizationSensorId}")
    @Operation(
            summary = "Get geolocalization sensor by ID",
            description = "Retrieves a specific geolocalization sensor's information by unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Geolocalization sensor found",
                    content = @Content(schema = @Schema(implementation = GeolocalizationSensorResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Geolocalization sensor not found")
    })
    public ResponseEntity<?> getGeolocalizationSensorById(
            @PathVariable
            @Parameter(description = "Geolocalization sensor unique identifier", example = "1", required = true)
            Long geolocalizationSensorId
    ) {
        var getGeolocalizationSensorByIdQuery = new GetGeolocalizationSensorByIdQuery(geolocalizationSensorId);
        var sensor = geolocalizationSensorQueryService.handle(getGeolocalizationSensorByIdQuery);

        if (sensor.isEmpty()) {
            var error = ApplicationError.notFound("GeolocalizationSensor", geolocalizationSensorId.toString());
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(error);
        }

        var sensorEntity = sensor.get();
        var sensorResource = GeolocalizationSensorResourceFromEntityAssembler.toResourceFromEntity(sensorEntity);
        return ResponseEntity.ok(sensorResource);
    }

    /**
     * Get all geolocalization sensors
     * @return A list of {@link GeolocalizationSensorResource} resources for all sensors
     */
    @GetMapping
    @Operation(
            summary = "Get all geolocalization sensors",
            description = "Retrieves a list of all geolocalization sensors recorded in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Geolocalization sensors found",
                    content = @Content(schema = @Schema(implementation = GeolocalizationSensorResource.class))
            )
    })
    public ResponseEntity<List<GeolocalizationSensorResource>> getAllGeolocalizationSensors() {
        var sensors = geolocalizationSensorQueryService.handle(new GetAllGeolocalizationSensorsQuery());

        if (sensors.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        var sensorResources = sensors.stream()
                .map(GeolocalizationSensorResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(sensorResources);
    }

    @GetMapping("/project/{projectId}")
    @Operation(
            summary = "Get all geolocalization sensors by Project ID",
            description = "Retrieves a list of all active IoT geolocalization sensors associated with a specific architectural project."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Geolocalization sensors for project retrieved successfully",
                    content = @Content(schema = @Schema(implementation = GeolocalizationSensorResource.class))
            )
    })
    public ResponseEntity<List<GeolocalizationSensorResource>> getGeolocalizationSensorsByProjectId(
            @PathVariable
            @Parameter(description = "Architectural project identifier", example = "PRJ-001", required = true)
            String projectId
    ) {
        var getGeolocalizationSensorsByProjectIdQuery = new GetGeolocalizationSensorsByProjectIdQuery(projectId);
        var sensors = geolocalizationSensorQueryService.handle(getGeolocalizationSensorsByProjectIdQuery);

        if (sensors.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        var sensorResources = sensors.stream()
                .map(GeolocalizationSensorResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(sensorResources);
    }

    /**
     * Update a geolocalization sensor
     *
     * @param geolocalizationSensorId The internal database ID
     * @param resource      The {@link UpdateGeolocalizationSensorResource} instance incoming payload
     * @return A {@link GeolocalizationSensorResource} resource for the updated node
     */
    @PutMapping("/{geolocalizationSensorId}")
    @Operation(
            summary = "Update geolocalization sensor",
            description = "Updates an existing geolocalization sensor node's data."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Geolocalization sensor updated successfully",
                    content = @Content(schema = @Schema(implementation = GeolocalizationSensorResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Geolocalization sensor not found"),
            @ApiResponse(responseCode = "409", description = "Conflict - Sensor ID already exists")
    })
    public ResponseEntity<?> updateGeolocalizationSensor(
            @PathVariable
            @Parameter(description = "Geolocalization sensor internal identifier", example = "1", required = true)
            Long geolocalizationSensorId,
            @Valid @RequestBody UpdateGeolocalizationSensorResource resource) {
        
        var updateCommand = UpdateGeolocalizationSensorCommandFromResourceAssembler.toCommandFromResource(geolocalizationSensorId, resource);
        var result = geolocalizationSensorCommandService.handle(updateCommand);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                GeolocalizationSensorResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    /**
     * Delete a geolocalization sensor
     *
     * @param geolocalizationSensorId The internal database ID
     * @return No content
     */
    @DeleteMapping("/{geolocalizationSensorId}")
    @Operation(
            summary = "Delete geolocalization sensor",
            description = "Deletes an existing geolocalization sensor node by its database unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Geolocalization sensor deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Geolocalization sensor not found")
    })
    public ResponseEntity<?> deleteGeolocalizationSensor(
            @PathVariable
            @Parameter(description = "Geolocalization sensor internal identifier", example = "1", required = true)
            Long geolocalizationSensorId) {
        
        var deleteCommand = new DeleteGeolocalizationSensorCommand(geolocalizationSensorId);
        var result = geolocalizationSensorCommandService.handle(deleteCommand);

        if (result instanceof Result.Failure<Void, ApplicationError> failure) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(failure.error());
        }

        return ResponseEntity.noContent().build();
    }
}