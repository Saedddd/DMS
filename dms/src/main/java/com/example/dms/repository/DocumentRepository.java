package com.example.dms.repository;

import com.example.dms.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository

public interface DocumentRepository extends JpaRepository<Document, UUID> {
    List<Document> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);
}
