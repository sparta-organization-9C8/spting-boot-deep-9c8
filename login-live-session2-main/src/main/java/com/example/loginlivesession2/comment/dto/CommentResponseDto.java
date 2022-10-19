package com.example.loginlivesession2.comment.dto;


import com.example.loginlivesession2.comment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private Long postId;
    private String username;
    private String content;

    public CommentResponseDto(Comment comment){
        this.postId = comment.getPost().getPostId();
        this.username = comment.getAccount().getUsername();
        this.content = comment.getContent();
    }

}
