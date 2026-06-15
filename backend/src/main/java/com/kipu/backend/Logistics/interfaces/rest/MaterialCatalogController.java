package com.kipu.backend.Logistics.interfaces.rest;

import com.kipu.backend.Logistics.application.commandservices.MaterialCatalogCommandService;
import com.kipu.backend.Logistics.application.queryservices.MaterialCatalogQueryService;
import com.kipu.backend.Logistics.application.queries.GetAllMaterialCatalogsQuery;
import com.kipu.backend.Logistics.application.queries.GetMaterialCatalogByIdQuery;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialCatalog;
import com.kipu.backend.Logistics.interfaces.rest.resources.CreateMaterialCatalogResource;
import com.kipu.backend.Logistics.interfaces.rest.resources.MaterialCatalogResource;
import com.kipu.backend.Logistics.interfaces.rest.transform.CreateMaterialCatalogCommandFromResourceAssembler;
import com.kipu.backend.Logistics.interfaces.rest.transform.ResponseEntityFromMaterialCatalogCommandResultAssembler;
import com.kipu.backend.Logistics.interfaces.rest.transform.ResponseEntityFromMaterialCatalogQueryResultAssembler;
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
@RequestMapping(value = "/api/v1/material-catalogs", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Material Catalogs", description = "Endpoints for material catalog management")
public class MaterialCatalogController {

    private final MaterialCatalogCommandService commandService;
    private final MaterialCatalogQueryService queryService;
    private final MessageSource messageSource;

    public MaterialCatalogController(MaterialCatalogCommandService commandService,
                                     MaterialCatalogQueryService queryService,
                                     MessageSource messageSource) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Create a new material catalog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Material catalog created",
                    content = @Content(schema = @Schema(implementation = MaterialCatalogResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Conflict - catalog with same name already exists",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<?> createMaterialCatalog(@Valid @RequestBody CreateMaterialCatalogResource resource) {
        log.debug("POST /api/v1/material-catalogs – name={}, categoryId={}, measureUnit={}",
                resource.name(), resource.categoryId(), resource.measureUnit());
        var result = commandService.handle(
                CreateMaterialCatalogCommandFromResourceAssembler.toCommandFromResource(resource));
        return ResponseEntityFromMaterialCatalogCommandResultAssembler.toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Get a material catalog by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Material catalog found",
                    content = @Content(schema = @Schema(implementation = MaterialCatalogResource.class))),
            @ApiResponse(responseCode = "404", description = "Material catalog not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getMaterialCatalogById(@PathVariable Long id) {
        log.debug("GET /api/v1/material-catalogs/{}", id);
        Optional<MaterialCatalog> catalog = queryService.handle(new GetMaterialCatalogByIdQuery(id));
        if (catalog.isEmpty()) {
            return ResponseEntityFromMaterialCatalogQueryResultAssembler.notFound(
                    messageSource, "material.catalog.error.notFoundById", id);
        }
        return ResponseEntityFromMaterialCatalogQueryResultAssembler.toResponseEntityFromMaterialCatalog(catalog.get());
    }

    @Operation(summary = "Get all material catalogs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of material catalogs",
                    content = @Content(schema = @Schema(implementation = MaterialCatalogResource[].class)))
    })
    @GetMapping
    public ResponseEntity<?> getAllMaterialCatalogs() {
        log.debug("GET /api/v1/material-catalogs");
        var catalogs = queryService.handle(new GetAllMaterialCatalogsQuery());
        return ResponseEntityFromMaterialCatalogQueryResultAssembler.toResponseEntityFromList(catalogs);
    }
}