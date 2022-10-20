package com.example.loginlivesession2.like.dto;

import com.example.loginlivesession2.like.entity.Like;
import com.example.loginlivesession2.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeResponseDto {

    private Long postId;

    public LikeResponseDto(Like like){

        this.postId = like.getPost().getPostId();

    }
}
