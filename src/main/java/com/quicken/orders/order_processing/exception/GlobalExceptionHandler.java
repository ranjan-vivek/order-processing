package com.quicken.orders.order_processing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOrderNotFoundException(NotFoundException notFoundException) {
        Map<String, String> errors = new HashMap<>();
        errors.put("status", String.valueOf(HttpStatus.NOT_FOUND.value()));
        errors.put("message", notFoundException.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UUIDFormatException.class)
    public ResponseEntity<Map<String, String>> handleUUIDFormatException(UUIDFormatException uuidFormatException) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", uuidFormatException.getMessage());
        errors.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }
}
