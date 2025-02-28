package com.example.dms.controller;

import com.example.dms.model.Comment;
import com.example.dms.service.CommentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/document/{documentId}")
    public List<Comment> getCommentsByDocumentId(@PathVariable UUID documentId) {
        return commentService.getCommentsByDocumentId(documentId);
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable UUID id) {
        commentService.deleteComment(id);
    }
}
