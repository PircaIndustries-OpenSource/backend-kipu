package com.kipu.backend.iam.interfaces.rest;

import com.kipu.backend.iam.application.commandservices.AuthCommandService;
import com.kipu.backend.iam.application.transform.AuthResource;
import com.kipu.backend.iam.application.transform.LoginCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling user authentication endpoints.
 */
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Endpoints for login operations")
public class AuthenticationController {

    private final AuthCommandService authCommandService;

    // Constructor injection
    public AuthenticationController(AuthCommandService authCommandService) {
        this.authCommandService = authCommandService;
    }

    /**
     * Authenticates user credentials and generates a JWT.
     */
    @PostMapping("/login")
    @Operation(summary = "Authenticates user and generates Bearer JWT token")
    public ResponseEntity<AuthResource> login(@Valid @RequestBody LoginCommand command) {
        AuthResource resource = authCommandService.handle(command);
        return ResponseEntity.ok(resource);
    }
}
