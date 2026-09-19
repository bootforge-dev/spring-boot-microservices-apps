package com.bootforge.inventoryservice.exception;

import com.bootforge.commons.exception.ErrorResponse;
import com.bootforge.commons.exception.ErrorResponseBuilder;
import com.bootforge.commons.exception.customer.CustomerNotFoundException;
import com.bootforge.commons.exception.customer.DuplicateCustomerException;
import com.bootforge.commons.exception.inventory.DuplicateInventoryException;
import com.bootforge.commons.exception.inventory.InsufficientInventoryException;
import com.bootforge.commons.exception.inventory.InventoryNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseBuilder.errorBuilder(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "Parameter validation failed",
                        request.getRequestURI(),
                        errors
                ));
    }

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInventoryNotFoundException(
            InventoryNotFoundException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseBuilder.errorBuilder(
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "Inventory not found",
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(
            IllegalStateException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponseBuilder.errorBuilder(
                        HttpStatus.CONFLICT.value(),
                        HttpStatus.CONFLICT.getReasonPhrase(),
                        "Cannot release more than reserved quantity",
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(DuplicateInventoryException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateInventoryException(
            DuplicateInventoryException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponseBuilder.errorBuilder(
                        HttpStatus.CONFLICT.value(),
                        HttpStatus.CONFLICT.getReasonPhrase(),
                        "Duplicate Inventory",
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(InsufficientInventoryException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientInventoryException(
            InsufficientInventoryException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.INSUFFICIENT_STORAGE)
                .body(ErrorResponseBuilder.errorBuilder(
                        HttpStatus.INSUFFICIENT_STORAGE.value(),
                        HttpStatus.INSUFFICIENT_STORAGE.getReasonPhrase(),
                        "Insufficient storage",
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseBuilder.errorBuilder(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        "Application internal exception",
                        request.getRequestURI()
                ));
    }


}
