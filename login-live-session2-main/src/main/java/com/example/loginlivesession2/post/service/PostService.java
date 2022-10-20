package com.example.loginlivesession2.post.service;

import com.example.loginlivesession2.global.dto.GlobalResDto;
import com.example.loginlivesession2.post.dto.PostDto;
import com.example.loginlivesession2.post.dto.PostResDto;
import com.example.loginlivesession2.post.entity.Post;
import com.example.loginlivesession2.post.repository.PostRepository;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;


    // 모든 글 읽어오기
    public List<Post> getAllpost() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    //게시글 작성
    @Transactional
    public Post createPost(PostDto requestDto, UserDetailsImpl userDetails) {

        String username = userDetails.getAccount().getUsername();
        Post post = new Post(requestDto, username);

        return postRepository.save(post);

//        return new GlobalResDto("Success Save Post", HttpStatus.OK.value());
    }

    //게시글 상세 조회
    @Transactional
    public ResponseEntity<PostResDto> getPost(Long id) {
        Post post =  checkPost(postRepository,id);
        PostResDto postResDto = new PostResDto(post);
        return ResponseEntity.ok(postResDto);
    }

    @Transactional
    public String updatePost(PostDto requestDto, Long id, UserDetailsImpl userDetails) {

        Post post = checkPost(postRepository,id);

        if(post.getUsername().equals(userDetails.getAccount().getUsername())){
            post.update(requestDto);
            return "댓글수정완료";
        } else {
            return "실패";
        }
//        Post post = checkPost(postRepository,id);
//        post.update(requestDto);
//
//        return new GlobalResDto("Success Update Post", HttpStatus.OK.value());
    }

    @Transactional
    public String deletePost(Long id,UserDetailsImpl userDetails) {

        Post post = checkPost(postRepository,id);

        if (post.getUsername().equals(userDetails.getAccount().getUsername())) {
            postRepository.deleteById(id);
            return "삭제완료";
        } else {
            return "실패";
        }


//        Post post  = checkPost(postRepository,id);
//        postRepository.delete(post);
//
//        return new GlobalResDto("Success Delete Post", HttpStatus.OK.value());
    }

    private Post checkPost(PostRepository postRepository, Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Not Found Post")
        );
    }
}

