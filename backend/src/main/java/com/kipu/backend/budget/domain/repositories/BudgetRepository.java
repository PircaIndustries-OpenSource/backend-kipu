package com.kipu.backend.budget.domain.repositories;

import com.kipu.backend.budget.domain.model.aggregates.Budget;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository {
    Budget save(Budget budget);
    Optional<Budget> findById(Long id);
    List<Budget> findByProjectId(String projectId);
}