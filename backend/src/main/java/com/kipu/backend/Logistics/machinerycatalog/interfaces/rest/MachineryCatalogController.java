package com.kipu.backend.Logistics.machinerycatalog.interfaces.rest;

import com.kipu.backend.Logistics.machinerycatalog.application.internal.commandservices.MachineryCatalogCommandService;
import com.kipu.backend.Logistics.machinerycatalog.application.internal.queryservices.MachineryCatalogQueryService;
import com.kipu.backend.Logistics.machinerycatalog.application.queries.GetAllMachineryCatalogQuery;
import com.kipu.backend.Logistics.machinerycatalog.application.queries.GetMachineryCatalogByIdQuery;
import com.kipu.backend.Logistics.machinerycatalog.interfaces.rest.resources.CreateMachineryCatalogResource;
import com.kipu.backend.Logistics.machinerycatalog.interfaces.rest.resources.MachineryCatalogResource;
import com.kipu.backend.Logistics.machinerycatalog.interfaces.rest.transform.CreateMachineryCatalogCommandFromResourceAssembler;
import com.kipu.backend.Logistics.machinerycatalog.interfaces.rest.transform.MachineryCatalogResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/machinery-catalog", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Machinery Catalog", description = "Endpoints for machinery catalog management")
public class MachineryCatalogController {

    private final MachineryCatalogCommandService commandService;
    private final MachineryCatalogQueryService queryService;

    public MachineryCatalogController(MachineryCatalogCommandService commandService,
                                      MachineryCatalogQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<List<MachineryCatalogResource>> getAll() {
        var query = new GetAllMachineryCatalogQuery();
        var catalogs = queryService.handle(query);
        var resources = catalogs.stream()
                .map(MachineryCatalogResourceFromEntityAssembler::toResource)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MachineryCatalogResource> getById(@PathVariable String id) {
        var query = new GetMachineryCatalogByIdQuery(id);
        return queryService.handle(query)
                .map(c -> ResponseEntity.ok(MachineryCatalogResourceFromEntityAssembler.toResource(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MachineryCatalogResource> create(
            @Valid @RequestBody CreateMachineryCatalogResource resource) {
        var command = CreateMachineryCatalogCommandFromResourceAssembler.toCommandFromResource(resource);
        return commandService.handle(command)
                .map(c -> new ResponseEntity<>(
                        MachineryCatalogResourceFromEntityAssembler.toResource(c),
                        HttpStatus.CREATED))
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean deleted = commandService.handleDelete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
