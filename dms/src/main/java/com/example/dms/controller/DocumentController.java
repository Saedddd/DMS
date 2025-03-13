package com.example.dms.controller;

import com.example.dms.model.Document;
import com.example.dms.service.DocumentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonView;
import com.example.dms.model.Views;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    @JsonView(Views.Basic.class)
    public List<Document> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    @GetMapping("/{id}")
    @JsonView(Views.Detailed.class)
    public Document getDocumentById(@PathVariable UUID id) {
        return documentService.getDocumentById(id);
    }

    @PostMapping
    @JsonView(Views.Detailed.class)
    public Document createDocument(@RequestBody Document document) {
        return documentService.createDocument(document);
    }

    @PutMapping("/{id}")
    @JsonView(Views.Detailed.class)
    public Document updateDocument(@PathVariable UUID id, @RequestBody Document document) {
        return documentService.updateDocument(id, document);
    }

    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable UUID id) {
        documentService.deleteDocument(id);
    }
}

