package com.product.product.exc;

import java.sql.SQLException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.product.product.dto.ErrorResponse;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler {
    
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(
            ProductNotFoundException ex, WebRequest request) {
        log.error("Product not found: {}", ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "Product Not Found",
            ex.getMessage(),
            request.getDescription(false)
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStockException(
            InsufficientStockException ex, WebRequest request) {
        log.error("Insufficient stock: {}", ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Insufficient Stock",
            ex.getMessage(),
            request.getDescription(false)
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(
            InvalidRequestException ex, WebRequest request) {
        log.error("Invalid request: {}", ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Invalid Request",
            ex.getMessage(),
            request.getDescription(false)
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        log.error("Illegal argument: {}", ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Invalid Argument",
            ex.getMessage(),
            request.getDescription(false)
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        log.error("Validation error: {}", ex.getMessage());
        
        StringBuilder errorMessage = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMsg = error.getDefaultMessage();
            errorMessage.append(fieldName).append(": ").append(errorMsg).append("; ");
        });
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Validation Error",
            errorMessage.toString(),
            request.getDescription(false)
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        log.error("Type mismatch: {}", ex.getMessage());
        
        String message = String.format("Parameter '%s' should be of type %s", 
            ex.getName(), ex.getRequiredType().getSimpleName());
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Type Mismatch",
            message,
            request.getDescription(false)
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex, WebRequest request) {
        log.error("Message not readable: {}", ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Invalid JSON",
            "Request body contains invalid JSON format",
            request.getDescription(false)
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex, WebRequest request) {
        log.error("Data integrity violation: {}", ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.CONFLICT.value(),
            "Data Integrity Violation",
            "The operation violates data integrity constraints",
            request.getDescription(false)
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> handleSQLException(
            SQLException ex, WebRequest request) {
        log.error("SQL error: {}", ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Database Error",
            "An error occurred while accessing the database",
            request.getDescription(false)
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(
            EntityNotFoundException ex, WebRequest request) {
        log.error("Entity not found: {}", ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "Entity Not Found",
            ex.getMessage(),
            request.getDescription(false)
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error",
            "An unexpected error occurred",
            request.getDescription(false)
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
