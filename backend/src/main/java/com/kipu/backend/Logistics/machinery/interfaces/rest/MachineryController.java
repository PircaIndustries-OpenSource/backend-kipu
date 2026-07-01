package com.kipu.backend.Logistics.machinery.interfaces.rest;

import com.kipu.backend.Logistics.machinery.application.commandservices.MachineryCommandService;
import com.kipu.backend.Logistics.machinery.application.queries.GetMachineryByProjectIdQuery;
import com.kipu.backend.Logistics.machinery.application.queries.GetMachineryByIdQuery;
import com.kipu.backend.Logistics.machinery.application.queryservices.MachineryQueryService;
import com.kipu.backend.Logistics.machinery.domain.model.aggregates.Machinery;
import com.kipu.backend.Logistics.machinery.interfaces.rest.resources.CreateMachineryResource;
import com.kipu.backend.Logistics.machinery.interfaces.rest.resources.MachineryResource;
import com.kipu.backend.Logistics.machinery.interfaces.rest.resources.UpdateMachineryResource;
import com.kipu.backend.Logistics.machinery.interfaces.rest.transform.CreateMachineryCommandFromResourceAssembler;
import com.kipu.backend.Logistics.machinery.interfaces.rest.transform.ResponseEntityFromMachineryCommandResultAssembler;
import com.kipu.backend.Logistics.machinery.interfaces.rest.transform.ResponseEntityFromMachineryQueryResultAssembler;
import com.kipu.backend.Logistics.machinery.interfaces.rest.transform.UpdateMachineryCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping(value = "/api/v1/machinery", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Machinery", description = "Endpoints for machinery management")
public class MachineryController {

    private final MachineryCommandService commandService;
    private final MachineryQueryService queryService;
    private final MessageSource messageSource;

    public MachineryController(MachineryCommandService commandService,
                               MachineryQueryService queryService,
                               MessageSource messageSource) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Create a new machinery entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Machinery created",
                    content = @Content(schema = @Schema(implementation = MachineryResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<?> createMachinery(@RequestParam String projectId,
                                             @Valid @RequestBody CreateMachineryResource resource) {
        log.debug("POST /api/v1/machinery – name={}, projectId={}", resource.name(), projectId);
        var result = commandService.handle(
                CreateMachineryCommandFromResourceAssembler.toCommandFromResource(resource, projectId));
        return ResponseEntityFromMachineryCommandResultAssembler.toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Get machinery by project ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Machinery list",
                    content = @Content(schema = @Schema(implementation = MachineryResource[].class)))
    })
    @GetMapping
    public ResponseEntity<?> getMachineryByProject(@RequestParam String projectId) {
        log.debug("GET /api/v1/machinery?projectId={}", projectId);
        var machineryList = queryService.handle(new GetMachineryByProjectIdQuery(projectId));
        return ResponseEntityFromMachineryQueryResultAssembler.toResponseEntityFromList(machineryList);
    }

    @Operation(summary = "Get machinery by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Machinery found",
                    content = @Content(schema = @Schema(implementation = MachineryResource.class))),
            @ApiResponse(responseCode = "404", description = "Machinery not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getMachineryById(@PathVariable String id) {
        log.debug("GET /api/v1/machinery/{}", id);
        Optional<Machinery> machinery = queryService.handle(new GetMachineryByIdQuery(id));
        if (machinery.isEmpty()) {
            return ResponseEntityFromMachineryQueryResultAssembler.notFound(
                    messageSource, "machinery.error.notFoundById", id);
        }
        return ResponseEntityFromMachineryQueryResultAssembler.toResponseEntityFromMachinery(machinery.get());
    }

    @Operation(summary = "Partially update a machinery entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Machinery updated",
                    content = @Content(schema = @Schema(implementation = MachineryResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Machinery not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<?> patchMachinery(@PathVariable String id,
                                            @RequestBody UpdateMachineryResource resource) {
        log.debug("PATCH /api/v1/machinery/{}", id);
        var command = UpdateMachineryCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handlePatch(id, command);
        return ResponseEntityFromMachineryCommandResultAssembler.toResponseEntityFromUpdateResult(result, messageSource);
    }

    @Operation(summary = "Delete a machinery entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Machinery deleted"),
            @ApiResponse(responseCode = "404", description = "Machinery not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMachinery(@PathVariable String id) {
        log.debug("DELETE /api/v1/machinery/{}", id);
        var result = commandService.handleDelete(id);
        return result.fold(
                unused -> ResponseEntity.noContent().build(),
                failure -> {
                    var status = failure instanceof com.kipu.backend.Logistics.machinery.application.commandservices.MachineryCommandFailure.NotFound
                            ? org.springframework.http.HttpStatus.NOT_FOUND
                            : org.springframework.http.HttpStatus.BAD_REQUEST;
                    return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(
                            status,
                            messageSource.getMessage(failure.messageKey(), null, failure.messageKey(),
                                    org.springframework.context.i18n.LocaleContextHolder.getLocale())));
                });
    }
}
