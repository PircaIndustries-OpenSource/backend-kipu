package com.kipu.backend.Logistics.interfaces.rest;

import com.kipu.backend.Logistics.application.commandservices.MaterialRequestCommandService;
import com.kipu.backend.Logistics.application.queryservices.MaterialRequestQueryService;
import com.kipu.backend.Logistics.application.queries.*;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialRequest;
import com.kipu.backend.Logistics.domain.model.valueobjects.RequestStatus;
import com.kipu.backend.Logistics.interfaces.rest.resources.CreateMaterialRequestResource;
import com.kipu.backend.Logistics.interfaces.rest.resources.MaterialRequestResource;
import com.kipu.backend.Logistics.interfaces.rest.resources.UpdateMaterialRequestResource;
import com.kipu.backend.Logistics.interfaces.rest.resources.UpdateMaterialRequestStatusResource;
import com.kipu.backend.Logistics.interfaces.rest.transform.CreateMaterialRequestCommandFromResourceAssembler;
import com.kipu.backend.Logistics.interfaces.rest.transform.ResponseEntityFromMaterialRequestCommandResultAssembler;
import com.kipu.backend.Logistics.interfaces.rest.transform.ResponseEntityFromMaterialRequestQueryResultAssembler;
import com.kipu.backend.Logistics.interfaces.rest.transform.ResponseEntityFromMaterialRequestUpdateCommandResultAssembler;
import com.kipu.backend.Logistics.interfaces.rest.transform.UpdateMaterialRequestCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/material-requests", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Material Requests", description = "Endpoints for material request management")
public class MaterialRequestController {

    private final MaterialRequestCommandService commandService;
    private final MaterialRequestQueryService queryService;
    private final MessageSource messageSource;

    public MaterialRequestController(MaterialRequestCommandService commandService,
                                     MaterialRequestQueryService queryService,
                                     MessageSource messageSource) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Create a new material request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Material request created",
                    content = @Content(schema = @Schema(implementation = MaterialRequestResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "402", description = "Insufficient budget",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<?> createMaterialRequest(@Valid @RequestBody CreateMaterialRequestResource resource) {
        log.debug("POST /api/v1/material-requests – budgetLineId={}, deadline={}, itemsCount={}",
                resource.budgetLineId(), resource.deadline(), resource.items().size());
        var result = commandService.handle(
                CreateMaterialRequestCommandFromResourceAssembler.toCommandFromResource(resource));
        return ResponseEntityFromMaterialRequestCommandResultAssembler.toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Get a material request by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Material request found",
                    content = @Content(schema = @Schema(implementation = MaterialRequestResource.class))),
            @ApiResponse(responseCode = "404", description = "Material request not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getMaterialRequestById(@PathVariable Long id) {
        log.debug("GET /api/v1/material-requests/{}", id);
        Optional<MaterialRequest> request = queryService.handle(new GetMaterialRequestByIdQuery(id));
        if (request.isEmpty()) {
            return ResponseEntityFromMaterialRequestQueryResultAssembler.notFound(
                    messageSource, "material.request.error.notFoundById", id);
        }
        return ResponseEntityFromMaterialRequestQueryResultAssembler.toResponseEntityFromMaterialRequest(request.get());
    }

    @Operation(summary = "Get all material requests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of material requests",
                    content = @Content(schema = @Schema(implementation = MaterialRequestResource[].class)))
    })
    @GetMapping
    public ResponseEntity<?> getAllMaterialRequests() {
        log.debug("GET /api/v1/material-requests");
        var requests = queryService.handle(new GetAllMaterialRequestsQuery());
        return ResponseEntityFromMaterialRequestQueryResultAssembler.toResponseEntityFromList(requests);
    }

    @Operation(summary = "Get material requests by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of material requests filtered by status",
                    content = @Content(schema = @Schema(implementation = MaterialRequestResource[].class))),
            @ApiResponse(responseCode = "400", description = "Invalid status",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping(params = "status")
    public ResponseEntity<?> getMaterialRequestsByStatus(
            @Parameter(description = "Request status (PENDING, ACCEPTED, REFUSED, CANCELLED)", required = true)
            @RequestParam String status) {
        log.debug("GET /api/v1/material-requests?status={}", status);
        RequestStatus requestStatus;
        try {
            requestStatus = RequestStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntityFromMaterialRequestQueryResultAssembler.badRequest(
                    messageSource, "material.request.error.invalidStatus", status);
        }
        var requests = queryService.handle(new GetMaterialRequestsByStatusQuery(requestStatus));
        return ResponseEntityFromMaterialRequestQueryResultAssembler.toResponseEntityFromList(requests);
    }

    @Operation(summary = "Update a material request (full replacement)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Material request updated",
                    content = @Content(schema = @Schema(implementation = MaterialRequestResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Material request not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMaterialRequest(@PathVariable Long id,
                                                   @Valid @RequestBody UpdateMaterialRequestResource resource) {
        log.debug("PUT /api/v1/material-requests/{}", id);
        var command = UpdateMaterialRequestCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handleUpdate(id, command);
        return ResponseEntityFromMaterialRequestUpdateCommandResultAssembler.toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Partially update a material request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Material request updated",
                    content = @Content(schema = @Schema(implementation = MaterialRequestResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Material request not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<?> patchMaterialRequest(@PathVariable Long id,
                                                   @RequestBody UpdateMaterialRequestResource resource) {
        log.debug("PATCH /api/v1/material-requests/{}", id);
        var command = UpdateMaterialRequestCommandFromResourceAssembler.toPatchCommandFromResource(resource);
        var result = commandService.handlePatch(id, command);
        return ResponseEntityFromMaterialRequestUpdateCommandResultAssembler.toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Update material request status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Material request status updated",
                    content = @Content(schema = @Schema(implementation = MaterialRequestResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid status",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Material request not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateMaterialRequestStatus(@PathVariable Long id,
                                                          @Valid @RequestBody UpdateMaterialRequestStatusResource resource) {
        log.debug("PATCH /api/v1/material-requests/{}/status", id);
        var result = commandService.handleStatusUpdate(id, resource.status());
        return ResponseEntityFromMaterialRequestUpdateCommandResultAssembler.toResponseEntityFromResult(result, messageSource);
    }
}