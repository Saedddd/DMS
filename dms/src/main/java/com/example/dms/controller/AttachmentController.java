package com.example.dms.controller;

import com.example.dms.model.Attachment;
import com.example.dms.service.AttachmentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @GetMapping("/document/{documentId}")
    public List<Attachment> getAttachmentsByDocumentId(@PathVariable UUID documentId) {
        return attachmentService.getAttachmentsByDocumentId(documentId);
    }

    @PostMapping
    public Attachment uploadAttachment(@RequestBody Attachment attachment) {
        return attachmentService.uploadAttachment(attachment);
    }

    @DeleteMapping("/{id}")
    public void deleteAttachment(@PathVariable UUID id) {
        attachmentService.deleteAttachment(id);
    }
}

