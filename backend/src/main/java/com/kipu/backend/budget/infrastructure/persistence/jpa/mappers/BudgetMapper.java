package com.kipu.backend.budget.infrastructure.persistence.jpa.mappers;

import com.kipu.backend.budget.domain.model.aggregates.Budget;
import com.kipu.backend.budget.domain.model.entities.ExpenseHistoryItem;
import com.kipu.backend.budget.infrastructure.persistence.jpa.entities.BudgetJpaEntity;
import com.kipu.backend.budget.infrastructure.persistence.jpa.entities.ExpenseHistoryJpaEntity;

import java.util.stream.Collectors;

public class BudgetMapper {

    public static Budget toDomain(BudgetJpaEntity entity) {
        if (entity == null) return null;
        Budget domain = new Budget();
        domain.setId(entity.getId());
        domain.setProjectId(entity.getProjectId());
        domain.setProgressId(entity.getProgressId());
        domain.setCode(entity.getCode());
        domain.setName(entity.getName());
        domain.setDescription(entity.getDescription());
        domain.setBudgeted(entity.getBudgeted());
        domain.setExecuted(entity.getExecuted());
        domain.setAvailable(entity.getAvailable());
        domain.setProgress(entity.getProgress());
        domain.setStatus(entity.getStatus());
        domain.setAlert(entity.getAlert());
        domain.setExpenseHistory(entity.getExpenseHistory().stream().map(e -> {
            ExpenseHistoryItem item = new ExpenseHistoryItem();
            item.setId(e.getId());
            item.setConcept(e.getConcept());
            item.setAmount(e.getAmount());
            item.setResponsible(e.getResponsible());
            item.setDescription(e.getDescription());
            item.setDate(e.getDate());
            return item;
        }).collect(Collectors.toList()));
        return domain;
    }

    public static BudgetJpaEntity toJpaEntity(Budget domain) {
        if (domain == null) return null;
        BudgetJpaEntity entity = new BudgetJpaEntity();
        entity.setId(domain.getId());
        entity.setProjectId(domain.getProjectId());
        entity.setProgressId(domain.getProgressId());
        entity.setCode(domain.getCode());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setBudgeted(domain.getBudgeted());
        entity.setExecuted(domain.getExecuted());
        entity.setAvailable(domain.getAvailable());
        entity.setProgress(domain.getProgress());
        entity.setStatus(domain.getStatus());
        entity.setAlert(domain.getAlert());
        entity.setExpenseHistory(domain.getExpenseHistory().stream().map(d -> {
            ExpenseHistoryJpaEntity e = new ExpenseHistoryJpaEntity();
            e.setId(d.getId());
            e.setConcept(d.getConcept());
            e.setAmount(d.getAmount());
            e.setResponsible(d.getResponsible());
            e.setDescription(d.getDescription());
            e.setDate(d.getDate());
            return e;
        }).collect(Collectors.toList()));
        return entity;
    }
}