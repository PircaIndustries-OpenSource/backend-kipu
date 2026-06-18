package com.kipu.backend.Logistics.interfaces.rest;

import com.kipu.backend.Logistics.application.commandservices.SupplierOfferCommandFailure;
import com.kipu.backend.Logistics.application.commandservices.SupplierOfferCommandService;
import com.kipu.backend.Logistics.application.queryservices.SupplierOfferQueryService;
import com.kipu.backend.Logistics.domain.model.aggregates.SupplierOffer;
import com.kipu.backend.Logistics.domain.model.valueobjects.MaterialCatalogId;
import com.kipu.backend.Logistics.domain.model.valueobjects.SupplierId;
import com.kipu.backend.Logistics.interfaces.rest.resources.CreateSupplierOfferResource;
import com.kipu.backend.Logistics.interfaces.rest.resources.SupplierOfferResource;
import com.kipu.backend.Logistics.interfaces.rest.resources.UpdateSupplierOfferResource;
import com.kipu.backend.Logistics.interfaces.rest.transform.CreateSupplierOfferCommandFromResourceAssembler;
import com.kipu.backend.Logistics.interfaces.rest.transform.ResponseEntityFromSupplierOfferCommandResultAssembler;
import com.kipu.backend.Logistics.interfaces.rest.transform.ResponseEntityFromSupplierOfferQueryResultAssembler;
import com.kipu.backend.Logistics.interfaces.rest.transform.UpdateSupplierOfferCommandFromResourceAssembler;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/supplier-offers", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Supplier Offers", description = "Endpoints for supplier offer management")
public class SupplierOfferController {

    private final SupplierOfferCommandService commandService;
    private final SupplierOfferQueryService queryService;
    private final MessageSource messageSource;

    public SupplierOfferController(SupplierOfferCommandService commandService,
                                   SupplierOfferQueryService queryService,
                                   MessageSource messageSource) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Create a new supplier offer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Supplier offer created",
                    content = @Content(schema = @Schema(implementation = SupplierOfferResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Conflict - offer for same supplier and material already exists",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<?> createSupplierOffer(@Valid @RequestBody CreateSupplierOfferResource resource) {
        log.debug("POST /api/v1/supplier-offers – supplierId={}, materialCatalogId={}, unitPrice={}",
                resource.supplierId(), resource.materialCatalogId(), resource.unitPrice());
        var result = commandService.handle(
                CreateSupplierOfferCommandFromResourceAssembler.toCommandFromResource(resource));
        return ResponseEntityFromSupplierOfferCommandResultAssembler.toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Get a supplier offer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supplier offer found",
                    content = @Content(schema = @Schema(implementation = SupplierOfferResource.class))),
            @ApiResponse(responseCode = "404", description = "Supplier offer not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getSupplierOfferById(@PathVariable Long id) {
        log.debug("GET /api/v1/supplier-offers/{}", id);
        Optional<SupplierOffer> offer = queryService.handle(id);
        if (offer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                            messageSource.getMessage("supplier.offer.error.notFound", null, "supplier.offer.error.notFound",
                                    java.util.Locale.getDefault())));
        }
        return ResponseEntityFromSupplierOfferQueryResultAssembler.toResponseEntityFromOffer(offer.get());
    }

    @Operation(summary = "Get all supplier offers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of supplier offers",
                    content = @Content(schema = @Schema(implementation = SupplierOfferResource[].class)))
    })
    @GetMapping
    public ResponseEntity<?> getAllSupplierOffers() {
        log.debug("GET /api/v1/supplier-offers");
        List<SupplierOffer> offers = queryService.handleGetAll();
        return ResponseEntityFromSupplierOfferQueryResultAssembler.toResponseEntityFromList(offers);
    }

    @Operation(summary = "Get supplier offers by supplier ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of supplier offers",
                    content = @Content(schema = @Schema(implementation = SupplierOfferResource[].class)))
    })
    @GetMapping(params = "supplierId")
    public ResponseEntity<?> getSupplierOffersBySupplierId(
            @Parameter(description = "Supplier ID", required = true)
            @RequestParam Integer supplierId) {
        log.debug("GET /api/v1/supplier-offers?supplierId={}", supplierId);
        List<SupplierOffer> offers = queryService.handleGetBySupplierId(new SupplierId(supplierId));
        return ResponseEntityFromSupplierOfferQueryResultAssembler.toResponseEntityFromList(offers);
    }

    @Operation(summary = "Get supplier offers by material catalog ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of supplier offers",
                    content = @Content(schema = @Schema(implementation = SupplierOfferResource[].class)))
    })
    @GetMapping(params = "materialCatalogId")
    public ResponseEntity<?> getSupplierOffersByMaterialCatalogId(
            @Parameter(description = "Material catalog ID", required = true)
            @RequestParam Integer materialCatalogId) {
        log.debug("GET /api/v1/supplier-offers?materialCatalogId={}", materialCatalogId);
        List<SupplierOffer> offers = queryService.handleGetByMaterialCatalogId(new MaterialCatalogId(materialCatalogId));
        return ResponseEntityFromSupplierOfferQueryResultAssembler.toResponseEntityFromList(offers);
    }

    @Operation(summary = "Update a supplier offer (full replacement)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supplier offer updated",
                    content = @Content(schema = @Schema(implementation = SupplierOfferResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Supplier offer not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSupplierOffer(@PathVariable Long id,
                                                  @Valid @RequestBody UpdateSupplierOfferResource resource) {
        log.debug("PUT /api/v1/supplier-offers/{}", id);
        var command = UpdateSupplierOfferCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handleUpdate(id, command);
        return ResponseEntityFromSupplierOfferCommandResultAssembler.toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Delete a supplier offer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Supplier offer deleted"),
            @ApiResponse(responseCode = "404", description = "Supplier offer not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSupplierOffer(@PathVariable Long id) {
        log.debug("DELETE /api/v1/supplier-offers/{}", id);
        var result = commandService.handleDelete(id);
        return result.fold(
                unused -> ResponseEntity.noContent().build(),
                failure -> {
                    var status = failure instanceof SupplierOfferCommandFailure.NotFound
                            ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
                    return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(
                            status, failure.messageKey()));
                });
    }
}
