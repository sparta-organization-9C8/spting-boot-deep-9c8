package com.example.loginlivesession2.post.entity;

import com.example.loginlivesession2.Timestamped;
import com.example.loginlivesession2.account.entity.Account;
import com.example.loginlivesession2.comment.entity.Comment;
import com.example.loginlivesession2.like.entity.Like;
import com.example.loginlivesession2.post.dto.PostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String title;
    @Column(nullable = true)
    private String contents;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Like> like = new ArrayList<>();

    public Post(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
    public Post(PostDto requestDto) {
        this.username = requestDto.getUsername();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void update(PostDto requestDto) {
        this.username = requestDto.getUsername();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
}