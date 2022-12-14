package com.example.loginlivesession2.comment.service;


import com.example.loginlivesession2.account.entity.Account;
import com.example.loginlivesession2.comment.dto.CommentDto;
import com.example.loginlivesession2.comment.dto.CommentResponseDto;
import com.example.loginlivesession2.comment.entity.Comment;
import com.example.loginlivesession2.comment.repository.CommentRepository;
import com.example.loginlivesession2.exception.CustomException;
import com.example.loginlivesession2.exception.ErrorCode;
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
    public CommentResponseDto createComment(CommentDto requestDto, Account account, Long postId) {

        // requestDto에 있는 postId로 post를 찾아옵니다. (postRepository 사용)
        Post post_get = postRepository.findById(postId).orElseThrow(
                ()-> new CustomException(ErrorCode.NotFoundPost)
        );

        Comment comment = new Comment(requestDto, post_get, account);
        commentRepository.save(comment);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);


        return commentResponseDto;
    }

    // 댓글 단건 조회


    // 댓글 전체 조회
    @Transactional
    public List<Comment> getAllComment() {

        return commentRepository.findAllByOrderByCreatedAtDesc();
    }


    // 댓글 수정
    @Transactional
    public Long update(CommentDto requestDto, Long commentId, Account currentAccount) {

        // 글을 찾아와서 그 글의 댓글을 다시 연결해서 확인하는 방법
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.NotFoundComment)
        );
        if(comment.getAccount().getUserId().equals(currentAccount.getUserId())){
            comment.update(requestDto);
            return comment.getCommentId();
        } else {
            throw new CustomException(ErrorCode.NotFoundCommentUser);
        }

    }

    //댓글 삭제
    @Transactional
    public void delete(Long commentId, Account currentAccount) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.NotFoundComment)
        );
        if (comment.getAccount().getUserId().equals(currentAccount.getUserId())) {
            commentRepository.deleteById(commentId);
        } else {
            throw new CustomException(ErrorCode.NotFoundCommentUser);
        }
    }

    // 재영: 댓글 단건 조회
    public Comment findComment(Long commentId) {
        // fetch type Eager일 때는 comment를 찾아올 때 post도 같이 찾아와서
        // lazy : comment 만 찾아옵니다
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글을 찾을 수 없습니다")
        );
        // lazy: comment에서 post를 사용할 때 찾아옵니다.
        Post post = comment.getPost();
        return comment;
    }
}
