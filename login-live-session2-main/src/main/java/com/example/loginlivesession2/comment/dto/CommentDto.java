package com.example.loginlivesession2.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDto {

    private Long postId;
    private String content;

    public CommentDto(Long postId, String content) {
        this.postId = postId;
        this.content = content;
    }
}