package com.example.dms.service;

import com.example.dms.exception.AttachmentNotFoundException;
import com.example.dms.model.Attachment;
import com.example.dms.repository.AttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;

    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public List<Attachment> getAttachmentsByDocumentId(UUID documentId) {
        return attachmentRepository.findByDocumentId(documentId);
    }
    
    public List<Attachment> searchAttachments(String query) {
        if (query == null || query.trim().isEmpty()) {
            return List.of();
        }
        return attachmentRepository.findByFileNameContainingIgnoreCase(query);
    }

    @Transactional
    public Attachment uploadAttachment(Attachment attachment) {
        return attachmentRepository.save(attachment);
    }

    @Transactional
    public void deleteAttachment(UUID id) {
        if (!attachmentRepository.existsById(id)) {
            throw new AttachmentNotFoundException(id);
        }
        attachmentRepository.deleteById(id);
    }
}

