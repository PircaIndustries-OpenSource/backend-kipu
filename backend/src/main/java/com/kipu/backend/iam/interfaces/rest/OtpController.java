package com.kipu.backend.iam.interfaces.rest;

import com.kipu.backend.iam.application.internal.outboundservices.otp.OtpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/identity/otp")
@Tag(name = "OTP", description = "Endpoints for OTP generation and validation")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/generate")
    @Operation(summary = "Generates a 6-digit OTP and sends it via email")
    public ResponseEntity<Void> generateOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        otpService.generateAndSendOtp(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/validate")
    @Operation(summary = "Validates a 6-digit OTP for an email")
    public ResponseEntity<Map<String, Boolean>> validateOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");
        
        if (email == null || code == null) {
            return ResponseEntity.badRequest().body(Map.of("valid", false));
        }

        boolean isValid = otpService.validateOtp(email, code);
        return ResponseEntity.ok(Map.of("valid", isValid));
    }
}
