package com.example.loginlivesession2.like.entity;

import com.example.loginlivesession2.account.entity.Account;
import com.example.loginlivesession2.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "Likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "member_id")
    private Account account;

    public Like(Post post, Account account) {
        this.post = post;
        this.account = account;
    }
}
