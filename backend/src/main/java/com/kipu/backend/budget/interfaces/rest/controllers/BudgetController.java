package com.kipu.backend.budget.interfaces.rest.controllers;

import com.kipu.backend.budget.domain.model.aggregates.Budget;
import com.kipu.backend.budget.domain.repositories.BudgetRepository;
import com.kipu.backend.budget.interfaces.rest.resources.BudgetResource;
import com.kipu.backend.budget.interfaces.rest.resources.CreateExpenseResource;
import com.kipu.backend.budget.interfaces.rest.resources.CreateExtensionResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/budgets")
@CrossOrigin(origins = {"http://localhost:4200", "https://mmanuel-fd.github.io"}, allowedHeaders = "*", allowCredentials = "true")
@Tag(name = "Budget", description = "Financial and Budgetary Allocation Endpoints")
public class BudgetController {

    private final BudgetRepository budgetRepository;

    public BudgetController(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @GetMapping
    @Operation(summary = "Get budgetary allocations filtered by project workspace identifier")
    public ResponseEntity<List<BudgetResource>> getBudgetsByProject(@RequestParam(required = false) String projectId) {
        String queryId = (projectId != null) ? projectId : "unknown";
        List<Budget> results = budgetRepository.findByProjectId(queryId);

        if (results.isEmpty() && !"unknown".equals(queryId)) {
            Budget sample = new Budget();
            sample.setProjectId(queryId);
            sample.setProgressId(1L);
            sample.setCode("BG-001");
            sample.setName("Initial Structural Foundation Budget");
            sample.setDescription("Base budget linked to excavation milestones");
            sample.setBudgeted(50000.0);
            sample.setAvailable(50000.0);
            budgetRepository.save(sample);
            results = budgetRepository.findByProjectId(queryId);
        }

        List<BudgetResource> resources = results.stream().map(this::mapToResource).collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get single budget baseline sheet details by its unique row identity")
    public ResponseEntity<BudgetResource> getBudgetById(@PathVariable Long id) {
        return budgetRepository.findById(id)
                .map(b -> ResponseEntity.ok(mapToResource(b)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/expenses")
    @Operation(summary = "Deduct money from available budget baseline to record a construction operational cost")
    public ResponseEntity<?> addExpenseToItem(@PathVariable Long id, @RequestBody CreateExpenseResource resource) {
        return budgetRepository.findById(id).map(budget -> {
            try {
                budget.addExpense(resource.concept(), resource.amount(), resource.responsible(), resource.description());
                Budget saved = budgetRepository.save(budget);
                return ResponseEntity.ok(mapToResource(saved));
            } catch (IllegalStateException ex) {
                return ResponseEntity.badRequest().body(ex.getMessage());
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/extensions")
    @Operation(summary = "Inject supplementary capital into an existing budget baseline with business authorization")
    public ResponseEntity<BudgetResource> addExtensionToItem(@PathVariable Long id, @RequestBody CreateExtensionResource resource) {
        return budgetRepository.findById(id).map(budget -> {
            budget.addExtension(resource.amount());
            Budget saved = budgetRepository.save(budget);
            return ResponseEntity.ok(mapToResource(saved));
        }).orElse(ResponseEntity.notFound().build());
    }

    private BudgetResource mapToResource(Budget b) {
        return new BudgetResource(
                b.getId(), b.getProjectId(), b.getProgressId(), b.getCode(), b.getName(), b.getDescription(),
                b.getBudgeted(), b.getExecuted(), b.getAvailable(), b.getProgress(), b.getStatus(), b.getAlert(),
                b.getExpenseHistory().stream().map(e -> new BudgetResource.ExpenseItemResource(
                        e.getId(), e.getConcept(), e.getAmount(), e.getResponsible(), e.getDescription(), e.getDate()
                )).collect(Collectors.toList())
        );
    }
}