package com.kipu.backend.iotmonitoring.hopperwatch.interfaces.rest;

import com.kipu.backend.iotmonitoring.hopperwatch.application.commandservices.HopperWatchSensorCommandService;
import com.kipu.backend.iotmonitoring.hopperwatch.application.queryservices.HopperWatchSensorQueryService;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.queries.GetAllHopperWatchSensorsQuery;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.queries.GetHopperWatchSensorByIdQuery;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.queries.GetHopperWatchSensorsByProjectIdQuery;
import com.kipu.backend.iotmonitoring.hopperwatch.interfaces.rest.resources.CreateHopperWatchSensorResource;
import com.kipu.backend.iotmonitoring.hopperwatch.interfaces.rest.resources.HopperWatchSensorResource;
import com.kipu.backend.iotmonitoring.hopperwatch.interfaces.rest.transform.CreateHopperWatchSensorCommandFromResourceAssembler;
import com.kipu.backend.iotmonitoring.hopperwatch.interfaces.rest.transform.HopperWatchSensorResourceFromEntityAssembler;
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
 * REST controller that exposes hopper watch resources and telemetry retrieval endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/hopper-watches", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Hopper Watches", description = "Hopper Watch IoT monitoring management endpoints")
public class HopperWatchSensorController {
    private final HopperWatchSensorCommandService hopperWatchCommandService;
    private final HopperWatchSensorQueryService hopperWatchQueryService;

    /**
     * Constructor
     *
     * @param hopperWatchCommandService The {@link HopperWatchSensorCommandService} instance
     * @param hopperWatchQueryService   The {@link HopperWatchSensorQueryService} instance
     */
    public HopperWatchSensorController(HopperWatchSensorCommandService hopperWatchCommandService, HopperWatchSensorQueryService hopperWatchQueryService) {
        this.hopperWatchCommandService = hopperWatchCommandService;
        this.hopperWatchQueryService = hopperWatchQueryService;
    }

    /**
     * Create a new hopper watch monitor
     *
     * @param resource The {@link CreateHopperWatchSensorResource} instance
     * @return A {@link HopperWatchSensorResource} resource for the created hopper watch
     */
    @PostMapping
    @Operation(
            summary = "Create a new hopper watch monitor",
            description = "Creates a new IoT hopper watch monitor associated with an architectural project and an explicit hardware sensor."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Hopper watch monitor created successfully",
                    content = @Content(schema = @Schema(implementation = HopperWatchSensorResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data or negative metrics"),
            @ApiResponse(responseCode = "409", description = "Conflict - Sensor hardware ID already registered")
    })
    public ResponseEntity<?> createHopperWatchSensor(@Valid @RequestBody CreateHopperWatchSensorResource resource) {
        var createHopperWatchSensorCommand = CreateHopperWatchSensorCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = hopperWatchCommandService.handle(createHopperWatchSensorCommand);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                HopperWatchSensorResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    /**
     * Get a hopper watch by ID
     *
     * @param hopperWatchId The hopper watch ID
     * @return A {@link HopperWatchSensorResource} resource for the hopper watch
     */
    @GetMapping("/{hopperWatchId}")
    @Operation(
            summary = "Get hopper watch by ID",
            description = "Retrieves specific real-time telemetry and state information for a hopper monitor by its unique database identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Hopper watch found",
                    content = @Content(schema = @Schema(implementation = HopperWatchSensorResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Hopper watch monitor not found")
    })
    public ResponseEntity<?> getHopperWatchSensorById(
            @PathVariable
            @Parameter(description = "Hopper watch unique identifier", example = "1", required = true)
            Long hopperWatchId
    ) {
        var getHopperWatchSensorByIdQuery = new GetHopperWatchSensorByIdQuery(hopperWatchId);
        var hopperWatch = hopperWatchQueryService.handle(getHopperWatchSensorByIdQuery);

        if (hopperWatch.isEmpty()) {
            var error = ApplicationError.notFound("Hopper Watch", hopperWatchId.toString());
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(error);
        }

        var hopperWatchEntity = hopperWatch.get();
        var hopperWatchResource = HopperWatchSensorResourceFromEntityAssembler.toResourceFromEntity(hopperWatchEntity);
        return ResponseEntity.ok(hopperWatchResource);
    }

    @GetMapping("/project/{projectId}")
    @Operation(
            summary = "Get all hopper watches by Project ID",
            description = "Retrieves a list of all active IoT hopper watch devices associated with a specific architectural project."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Hopper watches for project retrieved successfully",
                    content = @Content(schema = @Schema(implementation = HopperWatchSensorResource.class))
            )
    })
    public ResponseEntity<List<HopperWatchSensorResource>> getHopperWatchSensorsByProjectId(
            @PathVariable
            @Parameter(description = "Architectural project identifier", example = "PRJ-001", required = true)
            String projectId
    ) {
        var getHopperWatchSensorsByProjectIdQuery = new GetHopperWatchSensorsByProjectIdQuery(projectId);
        var hopperWatches = hopperWatchQueryService.handle(getHopperWatchSensorsByProjectIdQuery);

        if (hopperWatches.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        var hopperWatchResources = hopperWatches.stream()
                .map(HopperWatchSensorResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(hopperWatchResources);
    }

    /**
     * Get all hopper watches
     *
     * @return A list of {@link HopperWatchSensorResource} resources for all registered hopper monitors
     */
    @GetMapping
    @Operation(
            summary = "Get all hopper watches",
            description = "Retrieves a list of all active IoT hopper watch devices across all construction projects."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Hopper watches retrieved successfully",
                    content = @Content(schema = @Schema(implementation = HopperWatchSensorResource.class))
            )
    })
    public ResponseEntity<List<HopperWatchSensorResource>> getAllHopperWatchSensores() {
        var hopperWatches = hopperWatchQueryService.handle(new GetAllHopperWatchSensorsQuery());

        if (hopperWatches.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        var hopperWatchResources = hopperWatches.stream()
                .map(HopperWatchSensorResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(hopperWatchResources);
    }
}