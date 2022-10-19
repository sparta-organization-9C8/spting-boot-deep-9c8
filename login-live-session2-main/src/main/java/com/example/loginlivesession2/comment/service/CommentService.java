package com.example.loginlivesession2.comment.service;


import com.example.loginlivesession2.account.entity.Account;
import com.example.loginlivesession2.comment.dto.CommentDto;
import com.example.loginlivesession2.comment.entity.Comment;
import com.example.loginlivesession2.comment.repository.CommentRepository;
import com.example.loginlivesession2.post.entity.Post;
import com.example.loginlivesession2.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    // 댓글 생성
    @Transactional
    public Comment createComment(CommentDto requestDto, Account account, Long postId) {

        // requestDto에 있는 postId로 post를 찾아옵니다. (postRepository 사용)
        Post post_get = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 없습니다.")
        );

        Comment comment = new Comment(requestDto, post_get, account);

        return commentRepository.save(comment);
    }


    // 댓글 전체 조회
    @Transactional
    public List<Comment> getAllComment() {

        return commentRepository.findAllByOrderByCreatedAtDesc();
    }


    // 댓글 수정
    @Transactional
    public Long update(CommentDto requestDto, Long commentId, Long postId, Account currentAccount) {
        // requestDto에 있는 postId로 post를 찾아옵니다. (postRepository 사용)
        Post post_get = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 없습니다.")
        );
        // 글을 찾아와서 그 글의 댓글을 다시 연결해서 확인하는 방법
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        if(comment.getAccount().getUserId() == currentAccount.getUserId()){
            comment.update(requestDto);
            return comment.getCommentId();
            // return 어떻게 해야할 지?
        } else {
            throw new RuntimeException("댓글 작성자가 아닙니다.");
        }

    }

    //댓글 삭제
    @Transactional
    public void delete(Long commentId, Long postId, Account currentAccount) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 없습니다.")
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        if (comment.getAccount().getUserId().equals(currentAccount.getUserId())) {
            commentRepository.deleteById(commentId);
        } else {
            throw new RuntimeException("댓글 작성자가 아닙니다.");
        }
    }
}
