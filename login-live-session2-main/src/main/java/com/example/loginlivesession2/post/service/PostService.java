package com.example.loginlivesession2.post.service;

import com.example.loginlivesession2.global.dto.GlobalResDto;
import com.example.loginlivesession2.post.dto.PostDto;
import com.example.loginlivesession2.post.dto.PostResDto;
import com.example.loginlivesession2.post.entity.Post;
import com.example.loginlivesession2.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 모든 글 읽어오기 + 추가 예정
    public List<Post> getAllpost() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    //게시글 작성
    @Transactional
    public GlobalResDto createPost(PostDto requestDto) {

        Post Post = new Post(requestDto);
        postRepository.save(Post);

        return new GlobalResDto("Success Save Post", HttpStatus.OK.value());
    }

    //게시글 상세 조회
    @Transactional
    public ResponseEntity<PostResDto> getPost(Long id) {
        Post post =  postRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Not Found Post")
        );
        PostResDto postResDto = new PostResDto(post);

        return ResponseEntity.ok(postResDto);
    }

    @Transactional
    public Optional<Post> updatePost(PostDto requestDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("id 없습니다.")
        );
        post.update(requestDto);
        return postRepository.findById(id);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }


}

