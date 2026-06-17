package com.kipu.backend.progress.interfaces.rest.controllers;

import com.kipu.backend.progress.domain.model.aggregates.ProjectProgress;
import com.kipu.backend.progress.domain.repositories.ProjectProgressRepository;
import com.kipu.backend.progress.interfaces.rest.resources.CreateProjectProgressResource;
import com.kipu.backend.progress.interfaces.rest.resources.ProjectProgressResource;
import com.kipu.backend.progress.interfaces.rest.transform.ProjectProgressResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/progress")
@Tag(name = "Progress", description = "Project Advancement Management Endpoints")
public class ProjectProgressController {

    private final ProjectProgressRepository progressRepository;

    public ProjectProgressController(ProjectProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    @PostMapping
    @Operation(summary = "Create a new main project progress line or a hierarchy sub-advance entry")
    public ResponseEntity<ProjectProgressResource> createProgress(@RequestBody CreateProjectProgressResource resource) {
        ProjectProgress progress;
        if (resource.isMiniAdvance()) {
            progress = new ProjectProgress(
                    resource.projectId(), resource.projectName(), resource.activityName(),
                    resource.details(), resource.specialty(), resource.responsible(),
                    resource.workers(), resource.weather(), resource.weight()
            );
            progress.setMiniAdvance(true);
            progress.setParentId(resource.parentId());
            progress.setCurrentPercentage(resource.percentage() != null ? resource.percentage() : 0);
        } else {
            progress = new ProjectProgress(
                    resource.projectId(), resource.projectName(), resource.activityName(),
                    resource.details(), resource.specialty(), resource.responsible(),
                    resource.workers(), resource.weather(), resource.weight()
            );
        }

        ProjectProgress saved = progressRepository.save(progress);

        // Cascade transactional trigger for real-time tree synchronization
        if (saved.isMiniAdvance() && saved.getParentId() != null) {
            List<ProjectProgress> children = progressRepository.findByParentId(saved.getParentId());
            int sum = children.stream().mapToInt(ProjectProgress::getCurrentPercentage).sum();
            progressRepository.findById(saved.getParentId()).ifPresent(parent -> {
                parent.updateCompletionProgress(Math.min(sum, 100));
                progressRepository.save(parent);
            });
        }

        return new ResponseEntity<>(ProjectProgressResourceFromEntityAssembler.toResourceFromEntity(saved), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get specific progress entry by its unique identifier")
    public ResponseEntity<ProjectProgressResource> getProgressById(@PathVariable Long id) {
        return progressRepository.findById(id)
                .map(progress -> ResponseEntity.ok(ProjectProgressResourceFromEntityAssembler.toResourceFromEntity(progress)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "List progress tracking entries filtered by project workspace identifier")
    public ResponseEntity<List<ProjectProgressResource>> getProgressByProject(@RequestParam(required = false) String projectId) {
        List<ProjectProgress> results = (projectId != null) ?
                progressRepository.findByProjectId(projectId) :
                progressRepository.findByProjectId("unknown");

        List<ProjectProgressResource> resources = results.stream()
                .map(ProjectProgressResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/parent/{parentId}")
    @Operation(summary = "Fetch all hierarchy mini-advances belonging to a main activity parent node")
    public ResponseEntity<List<ProjectProgressResource>> getSubAdvancesByParent(@PathVariable Long parentId) {
        List<ProjectProgressResource> resources = progressRepository.findByParentId(parentId).stream()
                .map(ProjectProgressResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modify metrics and operational data logs inside an existing progress line")
    public ResponseEntity<ProjectProgressResource> updateProgress(@PathVariable Long id, @RequestBody CreateProjectProgressResource resource) {
        return progressRepository.findById(id).map(existing -> {
            existing.setActivityName(resource.activityName());
            existing.setDetails(resource.details());
            existing.setResponsible(resource.responsible());
            existing.setWeather(resource.weather());
            if (!existing.isMiniAdvance()) {
                existing.setWorkers(resource.workers());
                existing.setWeight(resource.weight());
            } else {
                existing.setCurrentPercentage(resource.percentage());
            }

            ProjectProgress saved = progressRepository.save(existing);
            if (saved.isMiniAdvance() && saved.getParentId() != null) {
                List<ProjectProgress> children = progressRepository.findByParentId(saved.getParentId());
                int sum = children.stream().mapToInt(ProjectProgress::getCurrentPercentage).sum();
                progressRepository.findById(saved.getParentId()).ifPresent(parent -> {
                    parent.updateCompletionProgress(Math.min(sum, 100));
                    progressRepository.save(parent);
                });
            }
            return ResponseEntity.ok(ProjectProgressResourceFromEntityAssembler.toResourceFromEntity(saved));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove a progress baseline log entry from the database system registries")
    public ResponseEntity<Void> deleteProgress(@PathVariable Long id) {
        progressRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}