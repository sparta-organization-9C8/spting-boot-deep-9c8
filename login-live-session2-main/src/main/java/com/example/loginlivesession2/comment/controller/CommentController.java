package com.example.loginlivesession2.comment.controller;

;
import com.example.loginlivesession2.comment.dto.CommentDto;
import com.example.loginlivesession2.comment.entity.Comment;
import com.example.loginlivesession2.comment.service.CommentService;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;


    // 댓글 생성
    @PostMapping("/{postId}/create")
    public Comment createComment(@RequestBody @Valid CommentDto requestDto,
                                 @PathVariable Long postId,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(requestDto, userDetails.getAccount(), postId);
        // 작성할 때 순서 조심하기 (그 위치에 있는 것을 가져오는 것이기 때문)
    }


    //댓글 전체 조회(API 테이블에는 나와있지 않음)
    @GetMapping("/{postId}")
    public List<Comment> getAllComment() {
        return commentService.getAllComment();
    }


    //댓글 수정
    @PutMapping("/{postId}/{commentId}")
    public String updateComment(@RequestBody CommentDto requestDto,
                              @PathVariable Long commentId,
                              @PathVariable Long postId,
                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.update(requestDto, commentId, postId, userDetails.getAccount());
        return "댓글 수정 "+commentId+" 번 아이디";
    }


    //댓글 삭제
    @DeleteMapping("/{postId}/{commentId}")
    public String deleteComment(@PathVariable Long commentId,
                              @PathVariable Long postId,
                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.delete(commentId, postId, userDetails.getAccount());
        return "댓글 삭제 "+commentId+" 번 아이디";
    }
}
