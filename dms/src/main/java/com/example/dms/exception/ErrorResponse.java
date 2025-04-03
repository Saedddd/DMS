package com.example.dms.exception;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String code;
    private String message;
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String detail;

    // Constructor for existing format
    public ErrorResponse(String code, String message, LocalDateTime timestamp) {
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Default timestamp constructor
    public ErrorResponse(String code, String message) {
        this(code, message, LocalDateTime.now());
    }
    
    // Constructor for new format (used in search functionality)
    public ErrorResponse(int status, String error, String detail) {
        this.status = status;
        this.error = error;
        this.detail = detail;
        this.timestamp = LocalDateTime.now();
        this.code = String.valueOf(status);
        this.message = error + ": " + detail;
    }
    
    // Default constructor
    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }
}