package com.example.loginlivesession2.post.dto;

import com.example.loginlivesession2.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResDto {

    private String username;
    private String title;
    private String contents;

    public PostResDto(Post post) {
        this.title = post.getTitle();
        this.contents = post.getContents();
    }
}
