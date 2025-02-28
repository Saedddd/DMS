package com.example.dms.service;

import com.example.dms.model.Comment;
import com.example.dms.repository.CommentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getCommentsByDocumentId(UUID documentId) {
        return commentRepository.findByDocumentId(documentId);
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteComment(UUID id) {
        commentRepository.deleteById(id);
    }
}

