package com.kipu.backend.shared.interfaces.rest;

import com.kipu.backend.shared.application.result.ApplicationError;
import com.kipu.backend.shared.interfaces.rest.transform.ErrorResponseAssembler;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Global exception handler for Kipu REST API.
 * Centralizes exception handling, translating unhandled exceptions
 * to consistent HTTP responses via the shared error assembly pattern.
 */
@Slf4j
@RestControllerAdvice
@NullMarked
public class GlobalExceptionHandler {
    private static final String MESSAGES_BASENAME = "messages";

    /**
     * Handles validation exceptions from Spring's request body validation.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        var fieldErrors = ex.getBindingResult().getFieldErrors();
        var validationPrefix = resolveMessageOrDefault("validation.field.prefix", "Field");
        var errorDetails = fieldErrors.isEmpty()
                ? resolveMessageOrDefault("validation.request.failed", "Request validation failed")
                : fieldErrors.stream()
                .map(error -> "%s %s: %s".formatted(
                        validationPrefix,
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .reduce((a, b) -> a + "; " + b)
                .orElse(resolveMessageOrDefault("validation.request.failed", "Request validation failed"));

        log.warn("Validation failed: {}", errorDetails);
        var applicationError = ApplicationError.validationError("request-body", errorDetails);
        return ErrorResponseAssembler.toErrorResponseFromApplicationError(applicationError);
    }

    /**
     * Handles invalid request arguments such as malformed UUID path or payload values.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        var errorDetails = ex.getMessage() != null ? ex.getMessage() : resolveMessageOrDefault("validation.request.failed", "Request validation failed");
        log.warn("Illegal argument: {}", errorDetails);
        var applicationError = ApplicationError.validationError(
                resolveMessageOrDefault("validation.request.argument", "request-argument"),
                errorDetails
        );
        return ErrorResponseAssembler.toErrorResponseFromApplicationError(applicationError);
    }

    /**
     * Handles unexpected runtime exceptions (500 Internal Server Error).
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        log.error("Unhandled runtime exception caught in global handler", ex);
        var applicationError = ApplicationError.unexpected(
                resolveMessageOrDefault("error.unexpected.context", "global-exception-handler"),
                ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred"
        );
        return ErrorResponseAssembler.toErrorResponseFromApplicationError(applicationError);
    }

    /**
     * Final fallback for any other unexpected exception type.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        log.error("Unhandled checked exception caught in global handler", ex);
        var applicationError = ApplicationError.unexpected(
                resolveMessageOrDefault("error.unexpected.context", "global-exception-handler"),
                ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred"
        );
        return ErrorResponseAssembler.toErrorResponseFromApplicationError(applicationError);
    }

    private String resolveMessageOrDefault(String key, String defaultValue, Object... args) {
        try {
            var bundle = ResourceBundle.getBundle(MESSAGES_BASENAME, LocaleContextHolder.getLocale());
            if (!bundle.containsKey(key)) {
                return defaultValue;
            }
            return MessageFormat.format(bundle.getString(key), args);
        } catch (MissingResourceException ex) {
            return defaultValue;
        }
    }
}