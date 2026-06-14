package com.kipu.backend.Logistics.infrastructure.persistence.jpa.repositories;

import com.kipu.backend.Logistics.domain.model.valueobjects.RequestStatus;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities.MaterialRequestJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataMaterialRequestJpaRepository extends JpaRepository<MaterialRequestJpaEntity, Long> {

    @Query("SELECT DISTINCT mr FROM MaterialRequestJpaEntity mr LEFT JOIN FETCH mr.items WHERE mr.requestStatus = :status")
    List<MaterialRequestJpaEntity> findByRequestStatus(@Param("status") RequestStatus requestStatus);

    @Query("SELECT DISTINCT mr FROM MaterialRequestJpaEntity mr LEFT JOIN FETCH mr.items WHERE mr.id = :id")
    Optional<MaterialRequestJpaEntity> findByIdWithItems(@Param("id") Long id);

    @Query("SELECT DISTINCT mr FROM MaterialRequestJpaEntity mr LEFT JOIN FETCH mr.items")
    List<MaterialRequestJpaEntity> findAllWithItems();
}