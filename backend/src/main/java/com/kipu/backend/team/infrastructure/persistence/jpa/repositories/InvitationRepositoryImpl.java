package com.kipu.backend.team.infrastructure.persistence.jpa.repositories;

import com.kipu.backend.team.domain.model.entities.Invitation;
import com.kipu.backend.team.domain.repositories.InvitationRepository;
import com.kipu.backend.team.infrastructure.persistence.jpa.entities.InvitationJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InvitationRepositoryImpl implements InvitationRepository {

    private final InvitationJpaRepository jpaRepository;

    public InvitationRepositoryImpl(InvitationJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    private Invitation toDomain(InvitationJpaEntity entity) {
        Invitation invitation = new Invitation(
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getRole(),
                entity.getStatus(),
                entity.getProjectId()
        );
        invitation.setId(entity.getId());
        return invitation;
    }

    private InvitationJpaEntity toJpa(Invitation invitation) {
        InvitationJpaEntity entity = new InvitationJpaEntity();
        if (invitation.getId() != null) {
            entity.setId(invitation.getId());
        }
        entity.setEmail(invitation.getEmail());
        entity.setFirstName(invitation.getFirstName());
        entity.setLastName(invitation.getLastName());
        entity.setRole(invitation.getRole());
        entity.setStatus(invitation.getStatus());
        entity.setProjectId(invitation.getProjectId());
        return entity;
    }

    @Override
    public Invitation save(Invitation invitation) {
        return toDomain(jpaRepository.save(toJpa(invitation)));
    }

    @Override
    public Optional<Invitation> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Invitation> findByProjectId(String projectId) {
        return jpaRepository.findByProjectId(projectId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Invitation> findByEmail(String email) {
        return jpaRepository.findByEmailIgnoreCase(email).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
