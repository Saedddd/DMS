package com.example.dms.exception;
import java.util.UUID;


public class DocumentNotFoundException extends RuntimeException {
    public DocumentNotFoundException(UUID id) {
        super("Document not found with id: " + id);
    }
} 