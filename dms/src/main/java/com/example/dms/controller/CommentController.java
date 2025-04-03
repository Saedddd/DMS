package com.example.dms.controller;

import com.example.dms.dtos.CommentDto;
import com.example.dms.model.Comment;
import com.example.dms.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonView;
import com.example.dms.exception.CommentNotFoundException;
import com.example.dms.exception.InvalidOperationException;
import com.example.dms.model.Views;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/comments")
@Validated
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/document/{documentId}")
    @JsonView(Views.Detailed.class)
    public ResponseEntity<List<Comment>> getCommentsByDocumentId(
            @PathVariable @NotNull UUID documentId) {
        List<Comment> comments = commentService.getCommentsByDocumentId(documentId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    @JsonView(Views.Detailed.class)
    public ResponseEntity<Comment> getCommentById(@PathVariable @NotNull UUID id) {
        return commentService.getCommentById(id)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new CommentNotFoundException(id));
    }

    @GetMapping("/search")
    @JsonView(Views.Detailed.class)
    public ResponseEntity<List<Comment>> searchComments(
            @RequestParam @Size(min = 2, message = "Search query must be at least 2 characters long") String query) {
        List<Comment> comments = commentService.searchComments(query);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    @JsonView(Views.Detailed.class)
    public ResponseEntity<Comment> createComment(@Valid @RequestBody CommentDto commentDto) {
        Comment savedComment = commentService.createComment(commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable @NotNull UUID id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
