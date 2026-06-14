package com.kipu.backend.Logistics.interfaces.rest;

import com.kipu.backend.Logistics.application.commandservices.SupplierCommandService;
import com.kipu.backend.Logistics.application.queryservices.SupplierQueryService;
import com.kipu.backend.Logistics.application.queries.*;
import com.kipu.backend.Logistics.domain.model.aggregates.Supplier;
import com.kipu.backend.Logistics.domain.model.valueobjects.Ruc;
import com.kipu.backend.Logistics.interfaces.rest.resources.CreateSupplierResource;
import com.kipu.backend.Logistics.interfaces.rest.resources.SupplierResource;
import com.kipu.backend.Logistics.interfaces.rest.resources.UpdateSupplierResource;
import com.kipu.backend.Logistics.interfaces.rest.transform.CreateSupplierCommandFromResourceAssembler;
import com.kipu.backend.Logistics.interfaces.rest.transform.SupplierResourceFromEntityAssembler;
import com.kipu.backend.Logistics.interfaces.rest.transform.ResponseEntityFromSupplierCommandResultAssembler;
import com.kipu.backend.Logistics.interfaces.rest.transform.ResponseEntityFromSupplierQueryResultAssembler;
import com.kipu.backend.Logistics.interfaces.rest.transform.ResponseEntityFromSupplierUpdateCommandResultAssembler;
import com.kipu.backend.Logistics.interfaces.rest.transform.UpdateSupplierCommandFromResourceAssembler;
import com.kipu.backend.shared.infrastructure.documentation.application.result.Result;
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
@RequestMapping(value = "/api/v1/suppliers", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Suppliers", description = "Endpoints for supplier management")
public class SupplierController {

    private final SupplierCommandService commandService;
    private final SupplierQueryService queryService;
    private final MessageSource messageSource;

    public SupplierController(SupplierCommandService commandService,
                              SupplierQueryService queryService,
                              MessageSource messageSource) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Create a new supplier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Supplier created",
                    content = @Content(schema = @Schema(implementation = SupplierResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Conflict - supplier with same RUC already exists",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<?> createSupplier(@Valid @RequestBody CreateSupplierResource resource) {
        log.debug("POST /api/v1/suppliers – ruc={}, socialReason={}, email={}",
                resource.ruc(), resource.socialReason(), resource.email());
        var result = commandService.handle(
                CreateSupplierCommandFromResourceAssembler.toCommandFromResource(resource));
        return ResponseEntityFromSupplierCommandResultAssembler.toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Get a supplier by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supplier found",
                    content = @Content(schema = @Schema(implementation = SupplierResource.class))),
            @ApiResponse(responseCode = "404", description = "Supplier not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getSupplierById(@PathVariable Long id) {
        log.debug("GET /api/v1/suppliers/{}", id);
        Optional<Supplier> supplier = queryService.handle(new GetSupplierByIdQuery(id));
        if (supplier.isEmpty()) {
            return ResponseEntityFromSupplierQueryResultAssembler.notFound(
                    messageSource, "supplier.error.notFoundById", id);
        }
        return ResponseEntityFromSupplierQueryResultAssembler.toResponseEntityFromSupplier(supplier.get());
    }

    @Operation(summary = "Get all suppliers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of suppliers",
                    content = @Content(schema = @Schema(implementation = SupplierResource[].class)))
    })
    @GetMapping
    public ResponseEntity<?> getAllSuppliers() {
        log.debug("GET /api/v1/suppliers");
        var suppliers = queryService.handle(new GetAllSuppliersQuery());
        return ResponseEntityFromSupplierQueryResultAssembler.toResponseEntityFromList(suppliers);
    }

    @Operation(summary = "Get a supplier by RUC")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supplier found",
                    content = @Content(schema = @Schema(implementation = SupplierResource.class))),
            @ApiResponse(responseCode = "404", description = "Supplier not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "400", description = "Invalid RUC",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/by-ruc")
    public ResponseEntity<?> getSupplierByRuc(@Parameter(description = "RUC to search for") @RequestParam String ruc) {
        log.debug("GET /api/v1/suppliers/by-ruc?ruc={}", mask(ruc));
        Ruc rucValue;
        try {
            rucValue = new Ruc(ruc);
        } catch (IllegalArgumentException e) {
            return ResponseEntityFromSupplierQueryResultAssembler.badRequest(
                    messageSource, "supplier.error.ruc.invalid", ruc);
        }
        Optional<Supplier> supplier = queryService.handle(new GetSupplierByRucQuery(rucValue));
        if (supplier.isEmpty()) {
            return ResponseEntityFromSupplierQueryResultAssembler.notFound(
                    messageSource, "supplier.error.notFoundByRuc", ruc);
        }
        return ResponseEntityFromSupplierQueryResultAssembler.toResponseEntityFromSupplier(supplier.get());
    }

    @Operation(summary = "Get suppliers by active status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of active/inactive suppliers",
                    content = @Content(schema = @Schema(implementation = SupplierResource[].class))),
            @ApiResponse(responseCode = "400", description = "Invalid isActive parameter",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/by-active-status")
    public ResponseEntity<?> getSuppliersByIsActive(@Parameter(description = "Filter by active status (true/false)") @RequestParam Boolean isActive) {
        log.debug("GET /api/v1/suppliers/by-active-status?isActive={}", isActive);
        if (isActive == null) {
            return ResponseEntityFromSupplierQueryResultAssembler.badRequest(
                    messageSource, "supplier.error.isActive.required");
        }
        var suppliers = queryService.handle(new GetSuppliersByIsActiveQuery(isActive));
        return ResponseEntityFromSupplierQueryResultAssembler.toResponseEntityFromList(suppliers);
    }

    @Operation(summary = "Update a supplier (full replacement)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supplier updated",
                    content = @Content(schema = @Schema(implementation = SupplierResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Supplier not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable Long id,
                                            @Valid @RequestBody UpdateSupplierResource resource) {
        log.debug("PUT /api/v1/suppliers/{}", id);
        var command = UpdateSupplierCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handleUpdate(id, command);
        return ResponseEntityFromSupplierUpdateCommandResultAssembler.toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Partially update a supplier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supplier updated",
                    content = @Content(schema = @Schema(implementation = SupplierResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Supplier not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<?> patchSupplier(@PathVariable Long id,
                                           @RequestBody UpdateSupplierResource resource) {
        log.debug("PATCH /api/v1/suppliers/{}", id);
        var command = UpdateSupplierCommandFromResourceAssembler.toPatchCommandFromResource(resource);
        var result = commandService.handlePatch(id, command);
        return ResponseEntityFromSupplierUpdateCommandResultAssembler.toResponseEntityFromResult(result, messageSource);
    }

    private static String mask(String value) {
        if (value == null || value.length() <= 4) return "****";
        return "****" + value.substring(value.length() - 4);
    }
}