package com.example.dms.service;

import com.example.dms.dtos.CommentDto;
import com.example.dms.exception.DocumentNotFoundException;
import com.example.dms.exception.UserNotFoundException;
import com.example.dms.model.Comment;
import com.example.dms.model.Document;
import com.example.dms.model.User;
import com.example.dms.repository.CommentRepository;
import com.example.dms.repository.DocumentRepository;
import com.example.dms.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;

    public CommentService(CommentRepository commentRepository,
                          UserRepository userRepository,
                          DocumentRepository documentRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
    }

    public List<Comment> getCommentsByDocumentId(UUID documentId) {
        return commentRepository.findByDocumentId(documentId);
    }

    public Optional<Comment> getCommentById(UUID id) {
        return commentRepository.findById(id);
    }
    
    public List<Comment> searchComments(String query) {
        if (query == null || query.trim().isEmpty()) {
            return List.of();
        }
        return commentRepository.findByTextContainingIgnoreCase(query);
    }

    @Transactional
    public Comment createComment(CommentDto commentDto) {
        Document document = documentRepository.findById(commentDto.getDocumentId())
                .orElseThrow(() -> new DocumentNotFoundException(commentDto.getDocumentId()));

        User author = userRepository.findById(commentDto.getAuthorId())
                .orElseThrow(() -> new UserNotFoundException(commentDto.getAuthorId()));

        Comment comment = Comment.builder()
                .document(document)
                .author(author)
                .text(commentDto.getText())
                .build();

        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(UUID id) {
        commentRepository.deleteById(id);
    }
}

