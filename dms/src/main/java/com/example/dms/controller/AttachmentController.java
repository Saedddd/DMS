package com.example.dms.controller;

import com.example.dms.model.Attachment;
import com.example.dms.service.AttachmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonView;
import com.example.dms.model.Views;
import com.example.dms.exception.ResourceNotFoundException;
import com.example.dms.exception.AttachmentNotFoundException;
import com.example.dms.exception.InvalidOperationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.annotation.NotNull;

@RestController
@RequestMapping("/attachments")
@Validated
public class AttachmentController {
    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @GetMapping("/document/{documentId}")
    @JsonView(Views.Basic.class)
    public ResponseEntity<List<Attachment>> getAttachmentsByDocumentId(
            @PathVariable @NotNull UUID documentId) {
        List<Attachment> attachments = attachmentService.getAttachmentsByDocumentId(documentId);
        return ResponseEntity.ok(attachments);
    }

    @GetMapping("/{id}")
    @JsonView(Views.Detailed.class)
    public ResponseEntity<Attachment> getAttachmentById(@PathVariable @NotNull UUID id) {
        return attachmentService.getAttachmentById(id)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new AttachmentNotFoundException(id));
    }

    @PostMapping
    @JsonView(Views.Detailed.class)
    public ResponseEntity<Attachment> uploadAttachment(
            @Valid @RequestBody Attachment attachment) {
        if (attachment.getFileName() == null || attachment.getFileName().trim().isEmpty()) {
            throw new InvalidOperationException("File name cannot be empty");
        }
        Attachment savedAttachment = attachmentService.uploadAttachment(attachment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAttachment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable @NotNull UUID id) {
        attachmentService.deleteAttachment(id);
        return ResponseEntity.noContent().build();
    }
}

