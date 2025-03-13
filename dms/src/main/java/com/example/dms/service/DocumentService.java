package com.example.dms.service;

import com.example.dms.model.Document;
import com.example.dms.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import com.example.dms.exception.DocumentNotFoundException;

@Service
@Slf4j
public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
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

    public Document createDocument(Document document) {
        return documentRepository.save(document);
    }

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

    public void deleteDocument(UUID id) {
        documentRepository.deleteById(id);
    }
}

