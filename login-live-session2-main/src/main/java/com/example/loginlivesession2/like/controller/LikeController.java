package com.example.loginlivesession2.like.controller;

import com.example.loginlivesession2.like.service.LikeService;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/like")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{postId}")
    public String createLike(@PathVariable Long postId,
                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.createLike(postId,userDetails);
    }

}
