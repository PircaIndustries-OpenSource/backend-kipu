package com.kipu.backend.Logistics.interfaces.rest;

import com.kipu.backend.Logistics.application.commandservices.MaterialInventoryCommandService;
import com.kipu.backend.Logistics.application.queryservices.MaterialInventoryQueryService;
import com.kipu.backend.Logistics.application.queries.GetAllMaterialInventoriesQuery;
import com.kipu.backend.Logistics.application.queries.GetMaterialInventoriesByCategoryIdQuery;
import com.kipu.backend.Logistics.application.queries.GetMaterialInventoryByIdQuery;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialInventory;
import com.kipu.backend.Logistics.domain.model.valueobjects.CategoryId;
import com.kipu.backend.Logistics.interfaces.rest.resources.CreateMaterialInventoryResource;
import com.kipu.backend.Logistics.interfaces.rest.resources.MaterialInventoryResource;
import com.kipu.backend.Logistics.interfaces.rest.transform.CreateMaterialInventoryCommandFromResourceAssembler;
import com.kipu.backend.Logistics.interfaces.rest.transform.ResponseEntityFromMaterialInventoryCommandResultAssembler;
import com.kipu.backend.Logistics.interfaces.rest.transform.ResponseEntityFromMaterialInventoryQueryResultAssembler;
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
@RequestMapping(value = "/api/v1/material-inventories", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Material Inventories", description = "Endpoints for material inventory management")
public class MaterialInventoryController {

    private final MaterialInventoryCommandService commandService;
    private final MaterialInventoryQueryService queryService;
    private final MessageSource messageSource;

    public MaterialInventoryController(MaterialInventoryCommandService commandService,
                                       MaterialInventoryQueryService queryService,
                                       MessageSource messageSource) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Create a new material inventory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Material inventory created",
                    content = @Content(schema = @Schema(implementation = MaterialInventoryResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Conflict - inventory for same project and material already exists",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<?> createMaterialInventory(@Valid @RequestBody CreateMaterialInventoryResource resource) {
        log.debug("POST /api/v1/material-inventories – projectId={}, materialCatalogId={}, location={}",
                resource.projectId(), resource.materialCatalogId(), resource.location());
        var result = commandService.handle(
                CreateMaterialInventoryCommandFromResourceAssembler.toCommandFromResource(resource));
        return ResponseEntityFromMaterialInventoryCommandResultAssembler.toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Get a material inventory by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Material inventory found",
                    content = @Content(schema = @Schema(implementation = MaterialInventoryResource.class))),
            @ApiResponse(responseCode = "404", description = "Material inventory not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getMaterialInventoryById(@PathVariable Long id) {
        log.debug("GET /api/v1/material-inventories/{}", id);
        Optional<MaterialInventory> inventory = queryService.handle(new GetMaterialInventoryByIdQuery(id));
        if (inventory.isEmpty()) {
            return ResponseEntityFromMaterialInventoryQueryResultAssembler.notFound(
                    messageSource, "material.inventory.error.notFoundById", id);
        }
        return ResponseEntityFromMaterialInventoryQueryResultAssembler.toResponseEntityFromMaterialInventory(inventory.get());
    }

    @Operation(summary = "Get all material inventories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of material inventories",
                    content = @Content(schema = @Schema(implementation = MaterialInventoryResource[].class)))
    })
    @GetMapping
    public ResponseEntity<?> getAllMaterialInventories() {
        log.debug("GET /api/v1/material-inventories");
        var inventories = queryService.handle(new GetAllMaterialInventoriesQuery());
        return ResponseEntityFromMaterialInventoryQueryResultAssembler.toResponseEntityFromList(inventories);
    }

    @Operation(summary = "Get material inventories by category ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of material inventories",
                    content = @Content(schema = @Schema(implementation = MaterialInventoryResource[].class))),
            @ApiResponse(responseCode = "400", description = "Invalid categoryId",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping(params = "categoryId")
    public ResponseEntity<?> getMaterialInventoriesByCategoryId(
            @Parameter(description = "Category ID", required = true)
            @RequestParam Integer categoryId) {
        log.debug("GET /api/v1/material-inventories?categoryId={}", categoryId);
        if (categoryId == null || categoryId <= 0) {
            return ResponseEntityFromMaterialInventoryQueryResultAssembler.badRequest(
                    messageSource, "material.inventory.error.categoryId.invalid");
        }
        var inventories = queryService.handle(new GetMaterialInventoriesByCategoryIdQuery(new CategoryId(categoryId)));
        return ResponseEntityFromMaterialInventoryQueryResultAssembler.toResponseEntityFromList(inventories);
    }
}