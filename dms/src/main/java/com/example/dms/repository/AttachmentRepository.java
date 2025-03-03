package com.example.dms.repository;

import com.example.dms.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
    List<Attachment> findByDocumentId(UUID documentId);
}

