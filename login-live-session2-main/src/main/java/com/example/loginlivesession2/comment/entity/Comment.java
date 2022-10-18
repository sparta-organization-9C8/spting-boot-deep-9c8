package com.example.loginlivesession2.comment.entity;


import com.example.loginlivesession2.Timestamped;
import com.example.loginlivesession2.comment.dto.CommentDto;
import com.example.loginlivesession2.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = true)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    public Comment(CommentDto requestDto, Post post_get) {
        this.content = requestDto.getContent();
        this.post = post_get; //??? Dto를 거치지 않고 그냥 가져오는건가?
    }

    public void update(CommentDto requestDto) {
        this.content = requestDto.getContent();
    }

}
