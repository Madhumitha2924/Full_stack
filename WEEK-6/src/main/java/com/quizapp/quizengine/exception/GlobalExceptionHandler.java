package com.quizapp.quizengine.exception;

import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    // ✅ Handles validation errors (@NotBlank, @Email, @Min)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", 400);
        response.put("error", "Validation Failed");

        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }
        response.put("details", fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // ✅ Handles ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(
            ResourceNotFoundException ex,
            WebRequest request) {

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", 404);
        response.put("error", ex.getMessage());
        response.put("details", request.getDescription(false));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // ✅ Handles any other unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(
            Exception ex,
            WebRequest request) {

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", 500);
        response.put("error", ex.getMessage());
        response.put("details", request.getDescription(false));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}