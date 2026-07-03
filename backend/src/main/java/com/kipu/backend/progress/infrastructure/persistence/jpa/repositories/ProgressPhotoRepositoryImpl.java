package com.kipu.backend.progress.infrastructure.persistence.jpa.repositories;

import com.kipu.backend.progress.domain.model.entities.ProgressPhoto;
import com.kipu.backend.progress.domain.repositories.ProgressPhotoRepository;
import com.kipu.backend.progress.infrastructure.persistence.jpa.entities.ProgressPhotoJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProgressPhotoRepositoryImpl implements ProgressPhotoRepository {

    private final ProgressPhotoJpaRepository jpaRepository;

    public ProgressPhotoRepositoryImpl(ProgressPhotoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    private ProgressPhoto toDomain(ProgressPhotoJpaEntity entity) {
        ProgressPhoto photo = new ProgressPhoto(
                entity.getProjectId(),
                entity.getTitle(),
                entity.getUrl(),
                entity.getUploadDate()
        );
        photo.setId(entity.getId());
        return photo;
    }

    private ProgressPhotoJpaEntity toJpa(ProgressPhoto photo) {
        ProgressPhotoJpaEntity entity = new ProgressPhotoJpaEntity();
        if (photo.getId() != null) {
            entity.setId(photo.getId());
        }
        entity.setProjectId(photo.getProjectId());
        entity.setTitle(photo.getTitle());
        entity.setUrl(photo.getUrl());
        entity.setUploadDate(photo.getUploadDate());
        return entity;
    }

    @Override
    public ProgressPhoto save(ProgressPhoto photo) {
        return toDomain(jpaRepository.save(toJpa(photo)));
    }

    @Override
    public Optional<ProgressPhoto> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<ProgressPhoto> findByProjectId(String projectId) {
        return jpaRepository.findByProjectId(projectId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
