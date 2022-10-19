package com.example.loginlivesession2.comment.service;


import com.example.loginlivesession2.comment.dto.CommentDto;
import com.example.loginlivesession2.comment.entity.Comment;
import com.example.loginlivesession2.comment.repository.CommentRepository;
import com.example.loginlivesession2.post.entity.Post;
import com.example.loginlivesession2.post.repository.PostRepository;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Comment createComment(CommentDto requestDto, Long id, UserDetailsImpl userDetails) {

        // requestDto에 있는 postId로 post를 찾아옵니다. (postRepository 사용)
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("id 없습니다.")
        );

        Comment comment = new Comment(requestDto, post, userDetails.getAccount().getUsername(),post.getPostId());

        return commentRepository.save(comment);
    }

    public List<Comment> getAllComment() {
        return commentRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional
    public Long update(CommentDto requestDto, Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("id 없습니다.")
        );
        comment.update(requestDto);
        return comment.getCommentId();
    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
