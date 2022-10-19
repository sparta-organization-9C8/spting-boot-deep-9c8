package com.example.loginlivesession2.account.dto;

import com.example.loginlivesession2.account.entity.Account;
import com.example.loginlivesession2.comment.entity.Comment;
import com.example.loginlivesession2.like.entity.Like;
import com.example.loginlivesession2.post.entity.Post;
import lombok.Getter;

import java.util.List;

@Getter
public class AccountResponseDto {
    private String username;
    private String email;
    private List<Post> post;
    private List<Comment> comment;
    private List<Like> likes;

    public AccountResponseDto(Account account) {
        this.username = account.getUsername();
        this.email = account.getEmail();
        this.post = account.getPost();
        this.comment = account.getComment();
        this.likes = account.getLikes();
    }
}
