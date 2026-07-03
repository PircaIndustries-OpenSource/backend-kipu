package com.kipu.backend.progress.infrastructure.persistence.jpa.entities;

import com.kipu.backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "progress_photos")
@Getter
@Setter
public class ProgressPhotoJpaEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private String projectId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String uploadDate;
}
