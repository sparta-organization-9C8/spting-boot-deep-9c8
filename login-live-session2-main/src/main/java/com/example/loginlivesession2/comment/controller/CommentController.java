package com.example.loginlivesession2.comment.controller;

;
import com.example.loginlivesession2.comment.dto.CommentDto;
import com.example.loginlivesession2.comment.dto.CommentResponseDto;
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
    @PostMapping("/{postId}")
    public CommentResponseDto createComment(@RequestBody @Valid CommentDto requestDto,
                                            @PathVariable Long postId,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(requestDto, userDetails.getAccount(), postId);
        // 작성할 때 순서 조심하기 (그 위치에 있는 것을 가져오는 것이기 때문)
    }

    // 재영: 댓글 단건 조회
    @GetMapping("/test/{commentId}")
    public Comment findComment(@PathVariable Long commentId) {
        return commentService.findComment(commentId);
    }


    //댓글 전체 조회(API 테이블에는 나와있지 않음)
    @GetMapping("/allcomment")
    public List<Comment> getAllComment() {
        return commentService.getAllComment();
    }


    //댓글 수정
    @PutMapping("/{commentId}")
    public String updateComment(@RequestBody @Valid CommentDto requestDto,
                                @PathVariable Long commentId,
                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.update(requestDto, commentId, userDetails.getAccount());
        return "댓글 수정 " + commentId + " 번 아이디";
    }


    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable Long commentId,
                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.delete(commentId, userDetails.getAccount());
        return "댓글 삭제 " + commentId + " 번 아이디";
    }
}
