package com.kipu.backend.Logistics.interfaces.rest;

import com.kipu.backend.Logistics.application.commandservices.MaterialCategoryCommandService;
import com.kipu.backend.Logistics.application.queryservices.MaterialCategoryQueryService;
import com.kipu.backend.Logistics.application.queries.GetAllMaterialCategoriesQuery;
import com.kipu.backend.Logistics.application.queries.GetMaterialCategoryByIdQuery;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialCategory;
import com.kipu.backend.Logistics.interfaces.rest.resources.CreateMaterialCategoryResource;
import com.kipu.backend.Logistics.interfaces.rest.resources.MaterialCategoryResource;
import com.kipu.backend.Logistics.interfaces.rest.transform.CreateMaterialCategoryCommandFromResourceAssembler;
import com.kipu.backend.Logistics.interfaces.rest.transform.MaterialCategoryResourceFromEntityAssembler;
import com.kipu.backend.Logistics.interfaces.rest.transform.ResponseEntityFromMaterialCategoryCommandResultAssembler;
import com.kipu.backend.Logistics.interfaces.rest.transform.ResponseEntityFromMaterialCategoryQueryResultAssembler;
import com.kipu.backend.shared.infrastructure.documentation.application.result.Result;
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
@RequestMapping(value = "/api/v1/material-categories", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Material Categories", description = "Endpoints for material category management")
public class MaterialCategoryController {

    private final MaterialCategoryCommandService commandService;
    private final MaterialCategoryQueryService queryService;
    private final MessageSource messageSource;

    public MaterialCategoryController(MaterialCategoryCommandService commandService,
                                      MaterialCategoryQueryService queryService,
                                      MessageSource messageSource) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Create a new material category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Material category created",
                    content = @Content(schema = @Schema(implementation = MaterialCategoryResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Conflict - category with same name already exists",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<?> createMaterialCategory(@Valid @RequestBody CreateMaterialCategoryResource resource) {
        log.debug("POST /api/v1/material-categories – name={}, description={}, isActive={}",
                resource.name(), resource.description(), resource.isActive());
        var result = commandService.handle(
                CreateMaterialCategoryCommandFromResourceAssembler.toCommandFromResource(resource));
        return ResponseEntityFromMaterialCategoryCommandResultAssembler.toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Get a material category by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Material category found",
                    content = @Content(schema = @Schema(implementation = MaterialCategoryResource.class))),
            @ApiResponse(responseCode = "404", description = "Material category not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getMaterialCategoryById(@PathVariable Long id) {
        log.debug("GET /api/v1/material-categories/{}", id);
        Optional<MaterialCategory> category = queryService.handle(new GetMaterialCategoryByIdQuery(id));
        if (category.isEmpty()) {
            return ResponseEntityFromMaterialCategoryQueryResultAssembler.notFound(
                    messageSource, "material.category.error.notFoundById", id);
        }
        return ResponseEntityFromMaterialCategoryQueryResultAssembler.toResponseEntityFromMaterialCategory(category.get());
    }

    @Operation(summary = "Get all material categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of material categories",
                    content = @Content(schema = @Schema(implementation = MaterialCategoryResource[].class)))
    })
    @GetMapping
    public ResponseEntity<?> getAllMaterialCategories() {
        log.debug("GET /api/v1/material-categories");
        var categories = queryService.handle(new GetAllMaterialCategoriesQuery());
        return ResponseEntityFromMaterialCategoryQueryResultAssembler.toResponseEntityFromList(categories);
    }
}