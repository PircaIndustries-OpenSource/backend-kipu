package com.kipu.backend.budget.infrastructure.persistence.jpa.adapters;

import com.kipu.backend.budget.domain.model.aggregates.Budget;
import com.kipu.backend.budget.domain.repositories.BudgetRepository;
import com.kipu.backend.budget.infrastructure.persistence.jpa.entities.BudgetJpaEntity;
import com.kipu.backend.budget.infrastructure.persistence.jpa.mappers.BudgetMapper;
import com.kipu.backend.budget.infrastructure.persistence.jpa.repositories.SpringDataBudgetJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BudgetRepositoryAdapter implements BudgetRepository {

    private final SpringDataBudgetJpaRepository repository;

    public BudgetRepositoryAdapter(SpringDataBudgetJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Budget save(Budget budget) {
        BudgetJpaEntity entity = BudgetMapper.toJpaEntity(budget);
        BudgetJpaEntity saved = repository.save(entity);
        return BudgetMapper.toDomain(saved);
    }

    @Override
    public Optional<Budget> findById(Long id) {
        return repository.findById(id).map(BudgetMapper::toDomain);
    }

    @Override
    public List<Budget> findByProjectId(String projectId) {
        return repository.findByProjectId(projectId).stream()
                .map(BudgetMapper::toDomain)
                .collect(Collectors.toList());
    }
}