package com.kipu.backend.team.interfaces.rest.controllers;

import com.kipu.backend.team.domain.model.entities.Invitation;
import com.kipu.backend.team.domain.repositories.InvitationRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/invitations")
@CrossOrigin(origins = {"http://localhost:4200", "https://mmanuel-fd.github.io"}, allowedHeaders = "*", allowCredentials = "true")
@Tag(name = "Invitations", description = "Team Invitations Endpoints")
public class InvitationController {

    private final InvitationRepository invitationRepository;

    public InvitationController(InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }

    @PostMapping
    @Operation(summary = "Send a new invitation")
    public ResponseEntity<?> sendInvitation(@RequestBody Invitation invitation) {
        List<Invitation> existingInvitations = invitationRepository.findByProjectId(invitation.getProjectId());
        boolean alreadyInvited = existingInvitations.stream()
                .anyMatch(i -> i.getEmail().equalsIgnoreCase(invitation.getEmail()));
        if (alreadyInvited) {
            return ResponseEntity.badRequest().body("User is already invited to this project.");
        }
        invitation.setStatus("PENDING");
        Invitation saved = invitationRepository.save(invitation);
        
        // Simulación de correo electrónico
        System.out.println("=========================================================");
        System.out.println("SIMULACIÓN DE CORREO: Invitación a unirse a Kipú");
        System.out.println("Para: " + invitation.getEmail());
        System.out.println("Mensaje: Hola " + invitation.getFirstName() + " " + invitation.getLastName() + ",");
        System.out.println("Has sido invitado a formar parte del proyecto con rol: " + invitation.getRole());
        System.out.println("Para aceptar o rechazar la invitación, haz clic en el siguiente enlace:");
        System.out.println("http://localhost:4200/invitations/" + saved.getId());
        System.out.println("=========================================================");
        
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get invitations by project ID")
    public ResponseEntity<List<Invitation>> getInvitationsByProject(@RequestParam String projectId) {
        List<Invitation> invitations = invitationRepository.findByProjectId(projectId);
        return ResponseEntity.ok(invitations);
    }

    @GetMapping("/user/{email}")
    @Operation(summary = "Get invitations by email")
    public ResponseEntity<List<Invitation>> getInvitationsByEmail(@PathVariable String email) {
        List<Invitation> invitations = invitationRepository.findByEmail(email);
        return ResponseEntity.ok(invitations);
    }
    
    @PutMapping("/{id}/accept")
    @Operation(summary = "Accept an invitation")
    public ResponseEntity<Invitation> acceptInvitation(@PathVariable Long id) {
        return invitationRepository.findById(id).map(existing -> {
            existing.setStatus("ACCEPTED");
            Invitation saved = invitationRepository.save(existing);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/reject")
    @Operation(summary = "Reject an invitation")
    public ResponseEntity<Invitation> rejectInvitation(@PathVariable Long id) {
        return invitationRepository.findById(id).map(existing -> {
            existing.setStatus("REJECTED");
            Invitation saved = invitationRepository.save(existing);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }
}
