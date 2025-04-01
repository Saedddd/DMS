package com.example.dms.exception;

import java.util.UUID;


public class AttachmentNotFoundException extends RuntimeException {
    public AttachmentNotFoundException(UUID id) {
        super("Attachment not found with id: " + id);
    }
} 