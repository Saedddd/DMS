package com.example.dms.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CommentDto {
    @NotNull(message = "Document ID is required")
    private UUID documentId;
    
    @NotNull(message = "Author ID is required")
    private UUID authorId;
    
    @NotBlank(message = "Comment text cannot be empty")
    private String text;
}

