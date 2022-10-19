package com.example.loginlivesession2.post.controller;


import com.example.loginlivesession2.Timestamped;
import com.example.loginlivesession2.account.entity.Account;
import com.example.loginlivesession2.post.dto.PostDto;
import com.example.loginlivesession2.post.dto.PostResponseDto;
import com.example.loginlivesession2.post.entity.Post;
import com.example.loginlivesession2.post.repository.PostRepository;
import com.example.loginlivesession2.post.service.PostService;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController extends Timestamped {

    private final PostService postService;
    private final PostRepository postRepository;

    //모든 글 읽어 오기
    @GetMapping("/post")
    public List<Post> getAllpost(){
        return postService.getAllpost();
    }

    // 게시글 작성
    @PostMapping("/post")
    public Post createPost(@RequestBody @Valid PostDto requestDto,
                           @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return postService.createPost(requestDto, userDetails.getAccount());
    }

    // 게시글 상세 조회
    @GetMapping("/post/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId){
        return postService.getOnePost(postId);
    }

    //글 수정
    @PutMapping("/auth/post/{postId}")
    public PostResponseDto updatePost(@RequestBody PostDto requestDto,
                                     @PathVariable Long postId,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.updatePost(requestDto, postId, userDetails.getAccount());
    }

    //글 삭제
    @DeleteMapping("/auth/post/{postId}")
    public void deletePost(@PathVariable Long postId,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails.getAccount());
    }
}
