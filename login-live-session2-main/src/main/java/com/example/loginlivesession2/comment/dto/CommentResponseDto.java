package com.example.loginlivesession2.comment.dto;


import com.example.loginlivesession2.comment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private Long commentId;
    private Long postId;
    private String content;

    public CommentResponseDto(Comment comment){
        this.commentId = comment.getCommentId();
        this.postId = comment.getPost().getPostId();
        this.content = comment.getContent();
    }

}
