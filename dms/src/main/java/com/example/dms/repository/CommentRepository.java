package com.example.dms.repository;

import com.example.dms.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByDocumentId(UUID documentId);
    
    List<Comment> findByTextContainingIgnoreCase(String text);
}

