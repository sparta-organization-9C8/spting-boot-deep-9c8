package com.example.loginlivesession2.comment.controller;

;
import com.example.loginlivesession2.comment.dto.CommentDto;
import com.example.loginlivesession2.comment.entity.Comment;
import com.example.loginlivesession2.comment.service.CommentService;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    // 댓글 등록
    @PostMapping("/api/auth/comment/{postId}")
    public Comment createComment(@RequestBody CommentDto requestDto,
                                 @PathVariable Long postId,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(requestDto,postId,userDetails);
    }

    // 댓글 전체 조회
    @GetMapping("/api/comment")
    public List<Comment> getAllComment() {
        return commentService.getAllComment();
    }

    // 댓글 수정(작성자만)
    @PutMapping("/api/auth/comment/{commentId}")
    public Long updateComment(@RequestBody CommentDto requestDto,
                              @PathVariable Long commentId,
                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.update(requestDto,commentId, userDetails);
    }

    // 댓글 삭제(작성자만)
    @DeleteMapping("/api/auth/comment/{commentId}")
    public String deleteComment(@PathVariable Long commentId,
                              @AuthenticationPrincipal UserDetailsImpl userDetails){

        return commentService.delete(commentId,userDetails);
    }
}
