package com.kipu.backend.iotmonitoring.seismiccontrol.interfaces.rest;

import com.kipu.backend.iotmonitoring.seismiccontrol.application.commandservices.SeismicControlSensorCommandService;
import com.kipu.backend.iotmonitoring.seismiccontrol.application.queryservices.SeismicControlSensorQueryService;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.queries.GetAllSeismicControlSensorsQuery;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.queries.GetSeismicControlSensorByIdQuery;
import com.kipu.backend.iotmonitoring.seismiccontrol.interfaces.rest.resources.CreateSeismicControlSensorResource;
import com.kipu.backend.iotmonitoring.seismiccontrol.interfaces.rest.resources.SeismicControlSensorResource;
import com.kipu.backend.iotmonitoring.seismiccontrol.interfaces.rest.transform.CreateSeismicControlSensorCommandFromResourceAssembler;
import com.kipu.backend.iotmonitoring.seismiccontrol.interfaces.rest.transform.SeismicControlSensorResourceFromEntityAssembler;
import com.kipu.backend.shared.application.result.ApplicationError;
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
 * REST controller that exposes seismic control sensor resources and telemetry retrieval endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/seismic-control-sensors", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Seismic Control Sensors", description = "Seismic control sensor nodes management endpoints")
public class SeismicControlSensorsController {

    private final SeismicControlSensorCommandService seismicControlSensorCommandService;
    private final SeismicControlSensorQueryService seismicControlSensorQueryService;

    /**
     * Constructor
     *
     * @param seismicControlSensorCommandService The {@link SeismicControlSensorCommandService} instance
     * @param seismicControlSensorQueryService   The {@link SeismicControlSensorQueryService} instance
     */
    public SeismicControlSensorsController(
            SeismicControlSensorCommandService seismicControlSensorCommandService,
            SeismicControlSensorQueryService seismicControlSensorQueryService) {
        this.seismicControlSensorCommandService = seismicControlSensorCommandService;
        this.seismicControlSensorQueryService = seismicControlSensorQueryService;
    }

    /**
     * Create a new seismic control sensor node
     *
     * @param resource The {@link CreateSeismicControlSensorResource} instance incoming payload
     * @return A {@link SeismicControlSensorResource} resource for the created node
     */
    @PostMapping
    @Operation(
            summary = "Create a new seismic control sensor node",
            description = "Creates a new IoT seismic sensor node bound to a structural project context with default telemetry data."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Seismic control sensor created successfully",
                    content = @Content(schema = @Schema(implementation = SeismicControlSensorResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Conflict - Sensor node already exists or code duplicated")
    })
    public ResponseEntity<?> createSeismicControlSensor(@Valid @RequestBody CreateSeismicControlSensorResource resource) {
        var createCommand = CreateSeismicControlSensorCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = seismicControlSensorCommandService.handle(createCommand);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                SeismicControlSensorResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    /**
     * Get a seismic control sensor by database internal ID
     *
     * @param seismicControlSensorId The internal database ID
     * @return A {@link SeismicControlSensorResource} resource for the sensor node
     */
    @GetMapping("/{seismicControlSensorId}")
    @Operation(
            summary = "Get seismic control sensor by ID",
            description = "Retrieves a specific seismic control sensor node's data by its database unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Seismic control sensor found",
                    content = @Content(schema = @Schema(implementation = SeismicControlSensorResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Seismic control sensor not found")
    })
    public ResponseEntity<?> getSeismicControlSensorById(
            @PathVariable
            @Parameter(description = "Seismic control sensor internal identifier", example = "1", required = true)
            Long seismicControlSensorId
    ) {
        var getByIdQuery = new GetSeismicControlSensorByIdQuery(seismicControlSensorId);
        var sensorOptional = seismicControlSensorQueryService.handle(getByIdQuery);

        if (sensorOptional.isEmpty()) {
            var error = ApplicationError.notFound("Seismic Control Sensor", seismicControlSensorId.toString());
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(error);
        }

        var sensorEntity = sensorOptional.get();
        var sensorResource = SeismicControlSensorResourceFromEntityAssembler.toResourceFromEntity(sensorEntity);
        return ResponseEntity.ok(sensorResource);
    }

    /**
     * Get all seismic control sensor nodes
     *
     * @return A list of {@link SeismicControlSensorResource} active resources
     */
    @GetMapping
    @Operation(
            summary = "Get all seismic control sensor nodes",
            description = "Retrieves a list of all active seismic control sensor tracking nodes across all projects."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Seismic control sensors fetched successfully",
                    content = @Content(schema = @Schema(implementation = SeismicControlSensorResource.class))
            )
    })
    public ResponseEntity<List<SeismicControlSensorResource>> getAllSeismicControlSensors() {
        var sensorsList = seismicControlSensorQueryService.handle(new GetAllSeismicControlSensorsQuery());

        if (sensorsList.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        var sensorResources = sensorsList.stream()
                .map(SeismicControlSensorResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(sensorResources);
    }
}