package com.example.dms.controller;

import com.example.dms.model.Document;
import com.example.dms.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonView;
import com.example.dms.model.Views;

@RestController
@RequestMapping("/documents")
@Validated
@Tag(name = "Document Controller", description = "API for document management operations")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    @JsonView(Views.Basic.class)
    @Operation(summary = "Get all documents", description = "Retrieves a list of all documents with basic information")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of documents")
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> documents = documentService.getAllDocuments();
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/{id}")
    @JsonView(Views.Detailed.class)
    @Operation(summary = "Get document by ID", description = "Retrieves detailed information about a specific document")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Document found"),
        @ApiResponse(responseCode = "404", description = "Document not found", 
                     content = @Content(schema = @Schema(implementation = Void.class)))
    })
    public ResponseEntity<Document> getDocumentById(
            @Parameter(description = "Document ID", required = true) 
            @PathVariable @NotNull UUID id) {
        Document document = documentService.getDocumentById(id);
        return ResponseEntity.ok(document);
    }

    @GetMapping("/search")
    @JsonView(Views.Basic.class)
    @Operation(summary = "Search documents", description = "Searches for documents matching the query in title or description")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Search results retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid query parameter", 
                     content = @Content(schema = @Schema(implementation = Void.class)))
    })
    public ResponseEntity<List<Document>> searchDocuments(
            @Parameter(description = "Search query", required = true)
            @RequestParam @Size(min = 2, message = "Search query must be at least 2 characters long") String query) {
        List<Document> documents = documentService.searchDocuments(query);
        return ResponseEntity.ok(documents);
    }

    @PostMapping
    @JsonView(Views.Detailed.class)
    @Operation(summary = "Create new document", description = "Creates a new document in the system")
    @ApiResponse(responseCode = "201", description = "Document created successfully")
    public ResponseEntity<Document> createDocument(
            @Parameter(description = "Document to create", required = true)
            @Valid @RequestBody Document document) {
        Document createdDocument = documentService.createDocument(document);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDocument);
    }

    @PutMapping("/{id}")
    @JsonView(Views.Detailed.class)
    @Operation(summary = "Update document", description = "Updates an existing document")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Document updated successfully"),
        @ApiResponse(responseCode = "404", description = "Document not found", 
                     content = @Content(schema = @Schema(implementation = Void.class)))
    })
    public ResponseEntity<Document> updateDocument(
            @Parameter(description = "Document ID", required = true)
            @PathVariable @NotNull UUID id, 
            @Parameter(description = "Updated document data", required = true)
            @Valid @RequestBody Document document) {
        Document updatedDocument = documentService.updateDocument(id, document);
        return ResponseEntity.ok(updatedDocument);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete document", description = "Deletes a document from the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Document deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Document not found", 
                     content = @Content(schema = @Schema(implementation = Void.class)))
    })
    public ResponseEntity<Void> deleteDocument(
            @Parameter(description = "Document ID", required = true)
            @PathVariable @NotNull UUID id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
}

