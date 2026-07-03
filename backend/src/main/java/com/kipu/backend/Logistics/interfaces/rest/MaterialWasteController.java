package com.kipu.backend.Logistics.interfaces.rest;

import com.kipu.backend.Logistics.application.commandservices.MaterialWasteCommandService;
import com.kipu.backend.Logistics.application.commands.DeleteMaterialWasteCommand;
import com.kipu.backend.Logistics.application.queries.GetAllMaterialWastesQuery;
import com.kipu.backend.Logistics.application.queries.GetMaterialWasteByIdQuery;
import com.kipu.backend.Logistics.application.queries.GetMaterialWastesByProjectIdQuery;
import com.kipu.backend.Logistics.application.queryservices.MaterialWasteQueryService;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.ProjectId;
import com.kipu.backend.Logistics.interfaces.rest.resources.CreateMaterialWasteResource;
import com.kipu.backend.Logistics.interfaces.rest.resources.MaterialWasteResource;
import com.kipu.backend.Logistics.interfaces.rest.resources.UpdateMaterialWasteResource;
import com.kipu.backend.Logistics.interfaces.rest.transform.CreateMaterialWasteCommandFromResourceAssembler;
import com.kipu.backend.Logistics.interfaces.rest.transform.ResponseEntityFromMaterialWasteCommandResultAssembler;
import com.kipu.backend.Logistics.interfaces.rest.transform.ResponseEntityFromMaterialWasteQueryResultAssembler;
import com.kipu.backend.Logistics.interfaces.rest.transform.UpdateMaterialWasteCommandFromResourceAssembler;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/material-wastes", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Material Wastes", description = "Endpoints for material waste report management")
public class MaterialWasteController {

    private final MaterialWasteCommandService commandService;
    private final MaterialWasteQueryService queryService;
    private final MessageSource messageSource;

    public MaterialWasteController(MaterialWasteCommandService commandService,
                                   MaterialWasteQueryService queryService,
                                   MessageSource messageSource) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.messageSource = messageSource;
    }

    // ── Commands ─────────────────────────────────────────────────────────────────

    @Operation(summary = "Create a new material waste record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Material waste record created",
                    content = @Content(schema = @Schema(implementation = MaterialWasteResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request – missing or invalid fields",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<?> createMaterialWaste(@Valid @RequestBody CreateMaterialWasteResource resource) {
        log.debug("POST /api/v1/material-wastes – projectId={}, materialCatalogId={}, type={}",
                resource.projectId(), resource.materialCatalogId(), resource.classificationType());
        var result = commandService.handle(
                CreateMaterialWasteCommandFromResourceAssembler.toCommandFromResource(resource));
        return ResponseEntityFromMaterialWasteCommandResultAssembler.toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Update an existing material waste record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Material waste record updated",
                    content = @Content(schema = @Schema(implementation = MaterialWasteResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Material waste not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMaterialWaste(
            @Parameter(description = "Material waste ID", required = true) @PathVariable Long id,
            @Valid @RequestBody UpdateMaterialWasteResource resource) {
        log.debug("PUT /api/v1/material-wastes/{}", id);
        var result = commandService.handle(
                UpdateMaterialWasteCommandFromResourceAssembler.toCommandFromResource(id, resource));
        return ResponseEntityFromMaterialWasteCommandResultAssembler.toResponseEntityFromUpdateResult(result, messageSource);
    }

    @Operation(summary = "Delete a material waste record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Material waste record deleted"),
            @ApiResponse(responseCode = "404", description = "Material waste not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMaterialWaste(
            @Parameter(description = "Material waste ID", required = true) @PathVariable Long id) {
        log.debug("DELETE /api/v1/material-wastes/{}", id);
        var result = commandService.handle(new DeleteMaterialWasteCommand(id));
        return ResponseEntityFromMaterialWasteCommandResultAssembler.toResponseEntityFromDeleteResult(result, messageSource);
    }

    // ── Queries ──────────────────────────────────────────────────────────────────

    @Operation(summary = "Get all material waste records")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of material waste records",
                    content = @Content(schema = @Schema(implementation = MaterialWasteResource[].class)))
    })
    @GetMapping
    public ResponseEntity<?> getAllMaterialWastes(
            @Parameter(description = "Optional project ID filter")
            @RequestParam(required = false) Integer projectId) {
        if (projectId != null) {
            log.debug("GET /api/v1/material-wastes?projectId={}", projectId);
            if (projectId <= 0) {
                return ResponseEntityFromMaterialWasteQueryResultAssembler.badRequest(
                        messageSource, "material.waste.error.projectId.invalidValue");
            }
            var wastes = queryService.handle(new GetMaterialWastesByProjectIdQuery(new ProjectId(projectId)));
            return ResponseEntityFromMaterialWasteQueryResultAssembler.toResponseEntityFromList(wastes);
        }
        log.debug("GET /api/v1/material-wastes");
        var wastes = queryService.handle(new GetAllMaterialWastesQuery());
        return ResponseEntityFromMaterialWasteQueryResultAssembler.toResponseEntityFromList(wastes);
    }

    @Operation(summary = "Get a material waste record by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Material waste record found",
                    content = @Content(schema = @Schema(implementation = MaterialWasteResource.class))),
            @ApiResponse(responseCode = "404", description = "Material waste record not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getMaterialWasteById(
            @Parameter(description = "Material waste ID", required = true) @PathVariable Long id) {
        log.debug("GET /api/v1/material-wastes/{}", id);
        var waste = queryService.handle(new GetMaterialWasteByIdQuery(id));
        if (waste.isEmpty()) {
            return ResponseEntityFromMaterialWasteQueryResultAssembler.notFound(
                    messageSource, "material.waste.error.notFoundById", id);
        }
        return ResponseEntityFromMaterialWasteQueryResultAssembler.toResponseEntityFromMaterialWaste(waste.get());
    }
}
