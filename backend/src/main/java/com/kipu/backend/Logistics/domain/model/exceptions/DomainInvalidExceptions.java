package com.kipu.backend.Logistics.domain.model.exceptions;

public abstract class DomainInvalidExceptions extends RuntimeException{
    public DomainInvalidExceptions(String message) {
        super(message);
    }
}