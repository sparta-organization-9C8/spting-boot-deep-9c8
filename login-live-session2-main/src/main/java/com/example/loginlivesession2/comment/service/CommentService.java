package com.example.loginlivesession2.comment.service;


import com.example.loginlivesession2.account.entity.Account;
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

    @Transactional
    public Comment createComment(CommentDto requestDto, Long postId, Account account) {

        // requestDto에 있는 postId로 post를 찾아옵니다. (postRepository 사용)
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("id 없습니다.")
        );

        Comment comment = new Comment(requestDto, account ,post);

        return commentRepository.save(comment);
    }

    @Transactional
    public List<Comment> getAllComment() {
        return commentRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional
    public Long update(CommentDto requestDto, Long commentId, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 없습니다.")
        );

        if (comment.getUsername().equals(userDetails.getAccount().getUsername())){
            comment.update(requestDto);
            return 1L;
        } else {
            return 2L;
        }
//
//        comment.update(requestDto);
//        return comment.getCommentId();
    }

    @Transactional
    public String delete(Long id, UserDetailsImpl userDetails) {

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 없습니다.")
        );

        if (comment.getUsername().equals(userDetails.getAccount().getUsername())) {
            commentRepository.deleteById(id);
            return "삭제완료";
        } else {
            return "실패";
        }
    }
}
