package com.example.loginlivesession2.account.dto;

import com.example.loginlivesession2.account.entity.Account;
import com.example.loginlivesession2.comment.dto.CommentResponseDto;
import com.example.loginlivesession2.comment.entity.Comment;
import com.example.loginlivesession2.like.entity.Like;
import com.example.loginlivesession2.post.dto.PostMyPageResDto;
import com.example.loginlivesession2.post.dto.PostResponseDto;
import com.example.loginlivesession2.post.entity.Post;
import lombok.Getter;

import java.util.List;

@Getter
public class AccountResponseDto {
    private String username;
    private String email;
    private List<PostMyPageResDto> myPosts;
    private List<CommentResponseDto> myComments;
    private List<Long> mylikePostId;

    public AccountResponseDto(Account account, List<PostMyPageResDto> myPosts, List<CommentResponseDto> myComments, List<Long> mylikePostId) {
        this.username = account.getUsername();
        this.email = account.getEmail();
        this.myPosts = myPosts;
        this.myComments = myComments;
        this.mylikePostId = mylikePostId;
    }
}
