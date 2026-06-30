package com.kipu.backend.logistics.machinery.interfaces.rest.controllers;

import com.kipu.backend.logistics.machinery.domain.model.entities.Machinery;
import com.kipu.backend.logistics.machinery.domain.repositories.MachineryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/machinery")
@CrossOrigin(origins = {"http://localhost:4200", "https://mmanuel-fd.github.io"}, allowedHeaders = "*", allowCredentials = "true")
@Tag(name = "Machinery", description = "Logistics Machinery Management Endpoints")
public class MachineryController {

    private final MachineryRepository machineryRepository;

    public MachineryController(MachineryRepository machineryRepository) {
        this.machineryRepository = machineryRepository;
    }

    @PostMapping
    @Operation(summary = "Create a new machinery entry")
    public ResponseEntity<Machinery> createMachinery(@RequestBody Machinery machinery) {
        Machinery saved = machineryRepository.save(machinery);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get machinery by project ID")
    public ResponseEntity<List<Machinery>> getMachineryByProject(@RequestParam(required = false) String projectId) {
        List<Machinery> machineryList = (projectId != null) ?
                machineryRepository.findByProjectId(projectId) :
                machineryRepository.findByProjectId("unknown");
        return ResponseEntity.ok(machineryList);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update an existing machinery entry")
    public ResponseEntity<Machinery> updateMachinery(@PathVariable String id, @RequestBody Machinery resource) {
        return machineryRepository.findById(id).map(existing -> {
            if (resource.getName() != null) existing.setName(resource.getName());
            if (resource.getStatus() != null) existing.setStatus(resource.getStatus());
            if (resource.getAssignedTo() != null) existing.setAssignedTo(resource.getAssignedTo());
            if (resource.getRegistrationDate() != null) existing.setRegistrationDate(resource.getRegistrationDate());
            if (resource.getMaintenanceHours() != null) existing.setMaintenanceHours(resource.getMaintenanceHours());
            if (resource.getAssignmentDetail() != null) existing.setAssignmentDetail(resource.getAssignmentDetail());

            Machinery saved = machineryRepository.save(existing);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a machinery entry")
    public ResponseEntity<Void> deleteMachinery(@PathVariable String id) {
        machineryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
