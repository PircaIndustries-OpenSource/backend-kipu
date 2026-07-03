package com.kipu.backend.team.domain.repositories;

import com.kipu.backend.team.domain.model.entities.Invitation;

import java.util.List;
import java.util.Optional;

public interface InvitationRepository {
    Invitation save(Invitation invitation);
    Optional<Invitation> findById(Long id);
    List<Invitation> findByProjectId(String projectId);
    List<Invitation> findByEmail(String email);
    void deleteById(Long id);
}
