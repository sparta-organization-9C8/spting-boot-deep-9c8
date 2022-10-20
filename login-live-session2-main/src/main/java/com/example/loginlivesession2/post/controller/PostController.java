package com.example.loginlivesession2.post.controller;


import com.example.loginlivesession2.Timestamped;
import com.example.loginlivesession2.global.dto.GlobalResDto;
import com.example.loginlivesession2.post.dto.PostDto;
import com.example.loginlivesession2.post.dto.PostResDto;
import com.example.loginlivesession2.post.entity.Post;
import com.example.loginlivesession2.post.repository.PostRepository;
import com.example.loginlivesession2.post.service.PostService;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController extends Timestamped {
//123123
    private final PostService postService;

    //모든 글 읽어 오기
    @GetMapping("/api/post")
    public List<Post> getAllpost(){
        return postService.getAllpost();
    }

    // 게시글 작성
    @PostMapping("/api/post")
    public Post createPost(@RequestBody PostDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.createPost(requestDto, userDetails);
    }

    // 게시글 상세 조회
    @GetMapping("/api/post/{id}")
    public ResponseEntity<PostResDto> getPost(@PathVariable Long id){
        return postService.getPost(id);
    }

    //글 수정
    @PutMapping("/api/auth/post/{id}")
    public String updatePost(@RequestBody PostDto requestDto,
                                   @PathVariable Long id,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.updatePost(requestDto, id, userDetails);
    }

    //글 삭제
    @DeleteMapping("/api/auth/post/{id}")
    public String deletePost(@PathVariable Long id,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.deletePost(id,userDetails);
    }
}
