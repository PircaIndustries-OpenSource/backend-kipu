package com.kipu.backend.budget.infrastructure.persistence.jpa.repositories;

import com.kipu.backend.budget.infrastructure.persistence.jpa.entities.BudgetJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataBudgetJpaRepository extends JpaRepository<BudgetJpaEntity, Long> {
    List<BudgetJpaEntity> findByProjectId(String projectId);
}