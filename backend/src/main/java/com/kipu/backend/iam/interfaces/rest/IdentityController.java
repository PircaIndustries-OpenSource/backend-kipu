package com.kipu.backend.iam.interfaces.rest;

import com.kipu.backend.iam.application.commandservices.UserCommandService;
import com.kipu.backend.iam.application.transform.IdentityRequest;
import com.kipu.backend.iam.application.transform.IdentityResource;
import com.kipu.backend.iam.application.transform.UpdateUserPasswordCommand;
import com.kipu.backend.iam.domain.model.aggregates.User;
import com.kipu.backend.iam.domain.model.exceptions.UserNotFoundException;
import com.kipu.backend.iam.domain.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for handling user identity endpoints directly consumed by the Angular frontend.
 */
@RestController
@RequestMapping("/api/v1/identity")
@Tag(name = "Identity", description = "Frontend-aligned endpoints for Identity Management")
public class IdentityController {

    private final UserCommandService userCommandService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor injection
    public IdentityController(UserCommandService userCommandService,
                              UserRepository userRepository,
                              PasswordEncoder passwordEncoder) {
        this.userCommandService = userCommandService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Gets user identity records by email, optionally verifying credentials for login.
     * Consumed by checkEmailExists, getIdentityByEmail, and login in the frontend.
     */
    @GetMapping
    @Operation(summary = "Query user identities by email and optionally verify password")
    public ResponseEntity<List<IdentityResource>> getIdentity(
            @RequestParam("email") String email,
            @RequestParam(value = "password", required = false) String password) {

        List<IdentityResource> result = new ArrayList<>();
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (password != null) {
                // If password parameter is provided, verify it (login behavior)
                if (passwordEncoder.matches(password, user.getPassword())) {
                    result.add(IdentityResource.fromUser(user));
                }
            } else {
                // Otherwise, just return the user resource
                result.add(IdentityResource.fromUser(user));
            }
        }

        return ResponseEntity.ok(result);
    }

    /**
     * Registers a new user identity (registerData in the frontend).
     */
    @PostMapping
    @Operation(summary = "Register a new user identity")
    public ResponseEntity<IdentityResource> registerIdentity(@Valid @RequestBody IdentityRequest request) {
        User user = userCommandService.handle(request.toRegisterUserCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(IdentityResource.fromUser(user));
    }

    /**
     * Resets/updates user password by ID (resetPassword in the frontend).
     */
    @PatchMapping("/{id}")
    @Operation(summary = "Reset password for a user identity by ID")
    public ResponseEntity<Void> resetPassword(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateUserPasswordCommand command) {
        userCommandService.handle(id, command);
        return ResponseEntity.noContent().build();
    }
}
