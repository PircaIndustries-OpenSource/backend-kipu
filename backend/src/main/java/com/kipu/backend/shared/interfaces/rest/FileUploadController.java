package com.kipu.backend.shared.interfaces.rest;

import com.kipu.backend.shared.infrastructure.cloudinary.CloudinaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/uploads")
@Tag(name = "Uploads", description = "Endpoints for file uploads using Cloudinary")
public class FileUploadController {

    private final CloudinaryService cloudinaryService;

    public FileUploadController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping
    @Operation(summary = "Upload a file to Cloudinary and get its URL")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String url = cloudinaryService.uploadFile(file);
            Map<String, String> response = new HashMap<>();
            response.put("url", url);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error uploading file");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
