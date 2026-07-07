package com.kipu.backend.progress.interfaces.rest.controllers;

import com.kipu.backend.progress.domain.model.entities.ProgressPhoto;
import com.kipu.backend.progress.domain.repositories.ProgressPhotoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Transactional(readOnly = true)
@RequestMapping("/api/v1/progress/photos")
@CrossOrigin(origins = {"http://localhost:4200", "https://mmanuel-fd.github.io"}, allowedHeaders = "*", allowCredentials = "true")
@Tag(name = "Progress Photos", description = "Project Advancement Photos Endpoints")
public class ProgressPhotoController {

    private final ProgressPhotoRepository photoRepository;

    public ProgressPhotoController(ProgressPhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Transactional
    @PostMapping
    @Operation(summary = "Create a new progress photo")
    public ResponseEntity<ProgressPhoto> createPhoto(@RequestBody ProgressPhoto photo) {
        ProgressPhoto saved = photoRepository.save(photo);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get photos by project ID")
    public ResponseEntity<List<ProgressPhoto>> getPhotosByProjectId(@RequestParam String projectId) {
        List<ProgressPhoto> photos = photoRepository.findByProjectId(projectId);
        return ResponseEntity.ok(photos);
    }

    @Transactional
    @PutMapping("/{id}")
    @Operation(summary = "Update photo title")
    public ResponseEntity<ProgressPhoto> updatePhotoTitle(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return photoRepository.findById(id).map(existing -> {
            if (body.containsKey("title")) {
                existing.setTitle(body.get("title"));
            }
            ProgressPhoto saved = photoRepository.save(existing);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a progress photo")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long id) {
        photoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
