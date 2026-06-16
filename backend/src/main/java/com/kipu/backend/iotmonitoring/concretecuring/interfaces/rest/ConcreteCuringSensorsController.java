package com.kipu.backend.iotmonitoring.concretecuring.interfaces.rest;

import com.kipu.backend.iotmonitoring.concretecuring.application.commandservices.ConcreteCuringSensorCommandService;
import com.kipu.backend.iotmonitoring.concretecuring.application.queryservices.ConcreteCuringSensorQueryService;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.queries.GetAllConcreteCuringSensorQueries;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.queries.GetConcreteCuringSensorByIdQuery;
import com.kipu.backend.iotmonitoring.concretecuring.interfaces.rest.resources.ConcreteCuringSensorResource;
import com.kipu.backend.iotmonitoring.concretecuring.interfaces.rest.resources.CreateConcreteCuringSensorResource;
import com.kipu.backend.iotmonitoring.concretecuring.interfaces.rest.transform.ConcreteCuringSensorResourceFromEntityAssembler;
import com.kipu.backend.iotmonitoring.concretecuring.interfaces.rest.transform.CreateConcreteCuringSensorCommandFromResourceAssembler;
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
 * REST controller that exposes concrete curing sensor resources and retrieval endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/concrete-curing-sensors", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Concrete Curing Sensors", description = "Concrete curing sensor management endpoints")
public class ConcreteCuringSensorsController {
    private final ConcreteCuringSensorCommandService concreteCuringSensorCommandService;
    private final ConcreteCuringSensorQueryService concreteCuringSensorQueryService;

    /**
     * Constructor.
     *
     * @param concreteCuringSensorCommandService The {@link ConcreteCuringSensorCommandService} instance
     * @param concreteCuringSensorQueryService   The {@link ConcreteCuringSensorQueryService} instance
     */
    public ConcreteCuringSensorsController(
            ConcreteCuringSensorCommandService concreteCuringSensorCommandService,
            ConcreteCuringSensorQueryService concreteCuringSensorQueryService) {
        this.concreteCuringSensorCommandService = concreteCuringSensorCommandService;
        this.concreteCuringSensorQueryService = concreteCuringSensorQueryService;
    }

    /**
     * Create a new concrete curing sensor monitor.
     *
     * @param resource The {@link CreateConcreteCuringSensorResource} instance
     * @return A {@link ConcreteCuringSensorResource} resource for the created monitor
     */
    @PostMapping
    @Operation(
            summary = "Create a new concrete curing sensor monitor",
            description = "Creates a new concrete curing process tracked by an IoT hardware sensor."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Concrete curing sensor monitor created successfully",
                    content = @Content(schema = @Schema(implementation = ConcreteCuringSensorResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Conflict - Sensor monitor already exists")
    })
    public ResponseEntity<?> createConcreteCuringSensor(@Valid @RequestBody CreateConcreteCuringSensorResource resource) {
        var createConcreteCuringSensorCommand = CreateConcreteCuringSensorCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = concreteCuringSensorCommandService.handle(createConcreteCuringSensorCommand);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ConcreteCuringSensorResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    /**
     * Get a concrete curing sensor monitor by ID.
     *
     * @param concreteCuringSensorId The technical identifier
     * @return A {@link ConcreteCuringSensorResource} resource for the monitor
     */
    @GetMapping("/{concreteCuringSensorId}")
    @Operation(
            summary = "Get concrete curing sensor by ID",
            description = "Retrieves a specific concrete curing sensor's tracking information by its unique technical identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Concrete curing sensor found",
                    content = @Content(schema = @Schema(implementation = ConcreteCuringSensorResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Concrete curing sensor not found")
    })
    public ResponseEntity<?> getConcreteCuringSensorById(
            @PathVariable
            @Parameter(description = "Concrete curing sensor unique technical identifier", example = "1", required = true)
            Long concreteCuringSensorId
    ) {
        var getConcreteCuringSensorByIdQuery = new GetConcreteCuringSensorByIdQuery(concreteCuringSensorId);
        var concreteCuringSensor = concreteCuringSensorQueryService.handle(getConcreteCuringSensorByIdQuery);

        if (concreteCuringSensor.isEmpty()) {
            var error = ApplicationError.notFound("ConcreteCuringSensor", concreteCuringSensorId.toString());
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(error);
        }

        var entity = concreteCuringSensor.get();
        var resource = ConcreteCuringSensorResourceFromEntityAssembler.toResourceFromEntity(entity);
        return ResponseEntity.ok(resource);
    }

    /**
     * Get all concrete curing sensor monitors.
     *
     * @return A list of {@link ConcreteCuringSensorResource} resources
     */
    @GetMapping
    @Operation(
            summary = "Get all concrete curing sensor monitors",
            description = "Retrieves a list of all active concrete curing sensors registered in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Concrete curing sensors found",
                    content = @Content(schema = @Schema(implementation = ConcreteCuringSensorResource.class))
            )
    })
    public ResponseEntity<List<ConcreteCuringSensorResource>> getAllConcreteCuringSensors() {
        var sensors = concreteCuringSensorQueryService.handle(new GetAllConcreteCuringSensorQueries());

        if (sensors.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        var sensorResources = sensors.stream()
                .map(ConcreteCuringSensorResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(sensorResources);
    }
}