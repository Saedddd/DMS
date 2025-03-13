package com.example.dms.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.slf4j.MDC;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({
        DocumentNotFoundException.class,
        UserNotFoundException.class,
        AttachmentNotFoundException.class,
        CommentNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNotFoundException(RuntimeException ex) {
        log.error("Resource not found: {}", ex.getMessage());
        return new ResponseEntity<>(
            new ErrorResponse(
                "RESOURCE_NOT_FOUND",
                ex.getMessage(),
                LocalDateTime.now()
            ),
            HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(InvalidOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidOperationException(InvalidOperationException ex) {
        log.error("Invalid operation: {}", ex.getMessage());
        return new ResponseEntity<>(
            new ErrorResponse(
                "INVALID_OPERATION",
                ex.getMessage(),
                LocalDateTime.now()
            ),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );
        
        log.error("Validation failed: {}", errors);
        return new ResponseEntity<>(
            new ValidationErrorResponse(
                "VALIDATION_ERROR",
                "Validation failed",
                LocalDateTime.now(),
                errors
            ),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InvalidStatusTransitionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidStatusTransitionException(
            InvalidStatusTransitionException ex) {
        log.error("Invalid status transition: {}", ex.getMessage());
        return new ResponseEntity<>(
            new ErrorResponse("INVALID_STATUS_TRANSITION", ex.getMessage()),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        String requestUri = ((ServletWebRequest) request).getRequest().getRequestURI();
        
        MDC.put("path", requestUri);
        MDC.put("errorType", "INTERNAL_SERVER_ERROR");
        
        log.error("Unhandled exception occurred: {}", ex.getMessage(), ex);
        
        MDC.clear();

        ErrorResponse errorResponse = new ErrorResponse(
            "INTERNAL_SERVER_ERROR",
            "An unexpected error occurred",
            LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, 
            WebRequest request) {
        String requestUri = ((ServletWebRequest) request).getRequest().getRequestURI();
        
        MDC.put("path", requestUri);
        MDC.put("errorType", "NOT_FOUND");
        
        log.warn("Resource not found: {}", ex.getMessage());
        
        MDC.clear();

        ErrorResponse errorResponse = new ErrorResponse(
            "NOT_FOUND",
            ex.getMessage(),
            LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
} 