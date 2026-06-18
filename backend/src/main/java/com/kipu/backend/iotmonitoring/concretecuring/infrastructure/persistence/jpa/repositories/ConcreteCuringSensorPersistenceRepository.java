package com.kipu.backend.iotmonitoring.concretecuring.infrastructure.persistence.jpa.repositories;

import com.kipu.backend.iotmonitoring.concretecuring.domain.model.valueobjects.SensorId;
import com.kipu.backend.iotmonitoring.concretecuring.infrastructure.persistence.jpa.entities.ConcreteCuringSensorPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data repository for concrete curing persistence entities.
 */
@Repository
public interface ConcreteCuringSensorPersistenceRepository extends JpaRepository<ConcreteCuringSensorPersistenceEntity, Long> {

    /**
     * Finds a concrete curing entity by its physical sensor identifier.
     *
     * @param sensorId The physical IoT sensor identifier.
     * @return An {@link Optional} containing the found entity, or empty if none matches.
     */
    @Query("select cc from ConcreteCuringSensorPersistenceEntity cc where cc.sensorId = :sensorId")
    Optional<ConcreteCuringSensorPersistenceEntity> findBySensorId(@Param("sensorId") SensorId sensorId);

    /**
     * Counts how many concrete curing entities use the given physical sensor identifier.
     *
     * @param sensorId The physical IoT sensor identifier.
     * @return The total count of matching records.
     */
    @Query("select count(cc) from ConcreteCuringSensorPersistenceEntity cc where cc.sensorId = :sensorId")
    long countBySensorId(@Param("sensorId") SensorId sensorId);

    @Query("select cc from ConcreteCuringSensorPersistenceEntity cc where cc.sensorId = :sensorId")
    List<ConcreteCuringSensorPersistenceEntity> findAllByProjectId(@Param("projectId") String projectId);
}