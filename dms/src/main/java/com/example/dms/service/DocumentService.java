package com.example.dms.service;

import com.example.dms.model.Document;
import com.example.dms.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
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
                .orElseThrow(() -> new RuntimeException("Документ не найден"));
    }

    public Document createDocument(Document document) {
        return documentRepository.save(document);
    }

    public Document updateDocument(UUID id, Document updatedDocument) {
        return documentRepository.findById(id).map(document -> {
            document.setTitle(updatedDocument.getTitle());
            document.setDescription(updatedDocument.getDescription());
            document.setStatus(updatedDocument.getStatus());
            document.setOwner(updatedDocument.getOwner());
            return documentRepository.save(document);
        }).orElseThrow(() -> new RuntimeException("Документ не найден"));
    }

    public void deleteDocument(UUID id) {
        documentRepository.deleteById(id);
    }
}

