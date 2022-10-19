package com.example.loginlivesession2.comment.controller;

;
import com.example.loginlivesession2.comment.dto.CommentDto;
import com.example.loginlivesession2.comment.entity.Comment;
import com.example.loginlivesession2.comment.service.CommentService;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;


    @PostMapping("/api/auth/comment")
    public Comment createComment(@RequestBody CommentDto requestDto,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(requestDto);
    }

    @GetMapping("/api/comment/{id}")
    public List<Comment> getAllComment() {
        return commentService.getAllComment();
    }

    @PutMapping("/api/auth/comment/{id}")
    public Long updateComment(@RequestBody CommentDto requestDto,
                              @PathVariable Long id,
                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.update(requestDto,id);
        return id;
    }

    @DeleteMapping("/api/auth/comment/{id}")
    public Long deleteComment(@PathVariable Long id,
                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.delete(id);
        return id;
    }
}
