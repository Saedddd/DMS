package com.example.dms.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CommentDto {
    private UUID documentId;
    private UUID authorId;
    private String text;
}

