package com.example.dms.service;

import com.example.dms.model.Document;
import com.example.dms.repository.DocumentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import com.example.dms.exception.DocumentNotFoundException;

@Service
@Slf4j
@Transactional(readOnly = true)
public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentService(
            DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Document getDocumentById(UUID id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Document not found with id: {}", id);
                    return new DocumentNotFoundException(id);
                });
    }

    public List<Document> searchDocuments(String query) {
        if (query == null || query.trim().isEmpty()) {
            return List.of();
        }
        log.info("Searching for documents with query: {}", query);
        return documentRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
    }

    @Transactional
    public Document createDocument(Document document) {
        log.info("Creating new document with title: {}", document.getTitle());
        Document savedDocument = documentRepository.save(document);
        return savedDocument;
    }

    @Transactional
    public Document updateDocument(UUID id, Document updatedDocument) {
        return documentRepository.findById(id)
                .map(document -> {
                    document.setTitle(updatedDocument.getTitle());
                    document.setDescription(updatedDocument.getDescription());
                    document.setStatus(updatedDocument.getStatus());
                    document.setOwner(updatedDocument.getOwner());
                    log.info("Updating document with id: {}", id);
                    return documentRepository.save(document);
                })
                .orElseThrow(() -> {
                    log.error("Document not found with id: {}", id);
                    return new DocumentNotFoundException(id);
                });
    }

    @Transactional
    public void deleteDocument(UUID id) {
        documentRepository.deleteById(id);
    }
}

