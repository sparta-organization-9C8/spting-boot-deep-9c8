package com.example.loginlivesession2.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDto {

    private String content;

    public CommentDto(String content) {
        this.content = content;
    }
}