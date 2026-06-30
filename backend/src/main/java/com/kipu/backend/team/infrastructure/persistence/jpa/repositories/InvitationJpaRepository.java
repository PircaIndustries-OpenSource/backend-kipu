package com.kipu.backend.team.infrastructure.persistence.jpa.repositories;

import com.kipu.backend.team.infrastructure.persistence.jpa.entities.InvitationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationJpaRepository extends JpaRepository<InvitationJpaEntity, Long> {
    List<InvitationJpaEntity> findByProjectId(String projectId);
    List<InvitationJpaEntity> findByEmailIgnoreCase(String email);
}
