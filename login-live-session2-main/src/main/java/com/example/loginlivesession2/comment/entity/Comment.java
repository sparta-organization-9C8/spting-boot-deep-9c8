package com.example.loginlivesession2.comment.entity;

import com.example.loginlivesession2.Timestamped;
import com.example.loginlivesession2.account.entity.Account;
import com.example.loginlivesession2.comment.dto.CommentDto;
import com.example.loginlivesession2.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column
    private String username;

    @Column(nullable = true)
    private String content;

    @Column
    private Long postId1;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;


    public Comment(CommentDto requestDto, Account account , Post post) {
        this.account = account;
        this.username = account.getUsername();
        this.content = requestDto.getContent();
        this.post = post;
        this.postId1 = post.getPostId();
    }

    public void update(CommentDto requestDto) {
        this.content = requestDto.getContent();
    }

}
