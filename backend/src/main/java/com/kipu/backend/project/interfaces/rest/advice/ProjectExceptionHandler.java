package com.kipu.backend.project.interfaces.rest.advice;

import com.kipu.backend.project.domain.model.exceptions.DuplicateProjectNameException;
import com.kipu.backend.project.domain.model.exceptions.InvalidProjectItemDatesException;
import com.kipu.backend.project.domain.model.exceptions.JustificationRequiredException;
import com.kipu.backend.project.domain.model.exceptions.ProjectNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Controller advice providing i18n exception handling for the Project Bounded Context.
 */
@RestControllerAdvice(basePackages = "com.kipu.backend.project")
public class ProjectExceptionHandler {

    private final MessageSource messageSource;

    public ProjectExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Structure for standard API errors.
     */
    public record ErrorDetails(String error, String message, int status) {}

    /**
     * Handles duplicate project name exception.
     */
    @ExceptionHandler(DuplicateProjectNameException.class)
    public ResponseEntity<ErrorDetails> handleDuplicateProjectName(DuplicateProjectNameException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(
                "project.error.duplicateName",
                null,
                ex.getMessage(),
                locale
        );
        ErrorDetails details = new ErrorDetails(
                "DUPLICATE_PROJECT_NAME",
                message,
                HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
    }

    /**
     * Handles justification required exception for status updates.
     */
    @ExceptionHandler(JustificationRequiredException.class)
    public ResponseEntity<ErrorDetails> handleJustificationRequired(JustificationRequiredException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(
                "project.error.justificationRequired",
                null,
                ex.getMessage(),
                locale
        );
        ErrorDetails details = new ErrorDetails(
                "JUSTIFICATION_REQUIRED",
                message,
                HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
    }

    /**
     * Handles invalid project item dates (logical order error).
     */
    @ExceptionHandler(InvalidProjectItemDatesException.class)
    public ResponseEntity<ErrorDetails> handleInvalidProjectItemDates(InvalidProjectItemDatesException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(
                "project.error.invalidDates",
                null,
                ex.getMessage(),
                locale
        );
        ErrorDetails details = new ErrorDetails(
                "INVALID_ITEM_DATES",
                message,
                HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
    }

    /**
     * Handles project not found exception.
     */
    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleProjectNotFound(ProjectNotFoundException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(
                "project.error.notFound",
                new Object[]{ex.getId()},
                ex.getMessage(),
                locale
        );
        ErrorDetails details = new ErrorDetails(
                "PROJECT_NOT_FOUND",
                message,
                HttpStatus.NOT_FOUND.value()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(details);
    }

    /**
     * Handles argument validation failures.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "VALIDATION_FAILED");
        body.put("status", HttpStatus.BAD_REQUEST.value());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        body.put("details", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * Handles business logic invalid inputs.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetails> handleIllegalArgumentException(IllegalArgumentException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String messageKey = ex.getMessage();
        String message = messageSource.getMessage(
                messageKey,
                null,
                messageKey,
                locale
        );
        ErrorDetails details = new ErrorDetails(
                "BAD_REQUEST",
                message,
                HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
    }

    /**
     * General fallback for unexpected system failures.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGeneralException(Exception ex) {
        ex.printStackTrace(); // Log stack trace for diagnostic debugging
        ErrorDetails details = new ErrorDetails(
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred on the server: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(details);
    }
}
