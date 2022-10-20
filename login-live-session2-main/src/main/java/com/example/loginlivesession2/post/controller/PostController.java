package com.example.loginlivesession2.post.controller;


import com.example.loginlivesession2.Timestamped;
import com.example.loginlivesession2.exception.CustomException;
import com.example.loginlivesession2.exception.ErrorCode;
import com.example.loginlivesession2.post.dto.PostCreateResDto;
import com.example.loginlivesession2.post.dto.PostDto;
import com.example.loginlivesession2.post.dto.PostResponseDto;
import com.example.loginlivesession2.post.entity.Post;
import com.example.loginlivesession2.post.repository.PostRepository;
import com.example.loginlivesession2.post.service.PostService;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController extends Timestamped {

    private final PostService postService;
    private final PostRepository postRepository;

    //모든 글 읽어 오기
    @GetMapping("/post")
    public List<PostResponseDto> getAllpost(){
        return postService.getAllpost();
    }

    // 게시글 작성
    @PostMapping("/post")
    public PostCreateResDto createPost(@RequestBody @Valid PostDto requestDto,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails
    ) { if(userDetails==null){
        throw new CustomException(ErrorCode.NotFoundToken);
    }
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
