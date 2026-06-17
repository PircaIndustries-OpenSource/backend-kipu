package com.kipu.backend.budget.domain.model.entities;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ExpenseHistoryItem {
    private Long id;
    private String concept;
    private Double amount;
    private String responsible;
    private String description;
    private Date date;

    public ExpenseHistoryItem() {
        this.date = new Date();
    }

    public ExpenseHistoryItem(String concept, Double amount, String responsible, String description) {
        this();
        this.concept = concept;
        this.amount = amount;
        this.responsible = responsible;
        this.description = description;
    }
}