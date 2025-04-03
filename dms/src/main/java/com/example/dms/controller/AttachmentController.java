package com.example.dms.controller;

import com.example.dms.model.Attachment;
import com.example.dms.service.AttachmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonView;
import com.example.dms.model.Views;
import com.example.dms.exception.AttachmentNotFoundException;
import com.example.dms.exception.InvalidOperationException;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/attachments")
@Validated
public class AttachmentController {
    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @GetMapping("/{id}")
    @JsonView(Views.Detailed.class)
    public ResponseEntity<List<Attachment>> getAttachmentsByDocumentId(@PathVariable @NotNull UUID id) {
        List<Attachment> attachments = attachmentService.getAttachmentsByDocumentId(id);
        if (attachments.isEmpty()) {
            throw new AttachmentNotFoundException(id);
        }
        return ResponseEntity.ok(attachments);
    }
    
    @GetMapping("/search")
    @JsonView(Views.Detailed.class)
    public ResponseEntity<List<Attachment>> searchAttachments(
            @RequestParam @Size(min = 2, message = "Search query must be at least 2 characters long") String query) {
        List<Attachment> attachments = attachmentService.searchAttachments(query);
        return ResponseEntity.ok(attachments);
    }

    @PostMapping
    @JsonView(Views.Detailed.class)
    public ResponseEntity<Attachment> uploadAttachment(@Valid @RequestBody Attachment attachment) {
        Attachment savedAttachment = attachmentService.uploadAttachment(attachment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAttachment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable @NotNull UUID id) {
        attachmentService.deleteAttachment(id);
        return ResponseEntity.noContent().build();
    }
}

