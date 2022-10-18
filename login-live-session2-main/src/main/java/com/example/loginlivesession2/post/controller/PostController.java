package com.example.loginlivesession2.post.controller;


import com.example.loginlivesession2.Timestamped;
import com.example.loginlivesession2.post.dto.PostDto;
import com.example.loginlivesession2.post.entity.Post;
import com.example.loginlivesession2.post.repository.PostRepository;
import com.example.loginlivesession2.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class PostController extends Timestamped {
//123123
    private final PostService postService;
    private final PostRepository postRepository;

    //모든 글 읽어 오기
    @GetMapping("/api/post")
    public List<Post> getAllpost(){
        return postService.getAllpost();
    }

    // 게시글 작성
    @PostMapping("/api/post")
    public Post createPost(@RequestBody PostDto requestDto) {
        return postService.createPost(requestDto);
    }

    // 게시글 상세 조회
    @GetMapping("/api/post/{id}")
    public Optional<Post> getPost(@PathVariable Long id){
        return postRepository.findById(id);
    }

    //글 수정
    @PutMapping("/api/auth/post/{id}")
    public Optional<Post> updatePost(@RequestBody PostDto requestDto, @PathVariable Long id){
        return postService.updatePost(requestDto, id);
    }

    //글 삭제
    @DeleteMapping("/api/auth/post/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }
}
