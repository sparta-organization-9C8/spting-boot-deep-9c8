package com.example.loginlivesession2.post.service;

import com.example.loginlivesession2.account.entity.Account;
import com.example.loginlivesession2.exception.CustomException;
import com.example.loginlivesession2.exception.ErrorCode;
import com.example.loginlivesession2.post.dto.PostCreateResDto;
import com.example.loginlivesession2.post.dto.PostDto;
import com.example.loginlivesession2.post.dto.PostResponseDto;
import com.example.loginlivesession2.post.entity.Post;
import com.example.loginlivesession2.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;



//    public PostService(PostRepository postRepository) {
//        this.postRepository = postRepository;
//    }



    // 모든 글 읽어오기 + 추가 예정
    @Transactional
    public List<PostResponseDto> getAllpost() {
        List<Post> allByOrderByCreatedAtDesc = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for (Post post : allByOrderByCreatedAtDesc) {
            postResponseDtos.add(new PostResponseDto(post));
        }
        return postResponseDtos;

    }

    //상세조회
    @Transactional
    public PostResponseDto getOnePost(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.NotFoundPost)
        );

        PostResponseDto postResponseDto = new PostResponseDto(post);

        return postResponseDto;

    }



    //글 쓰기
    @Transactional
    public PostCreateResDto createPost(PostDto requestDto, Account account) {

        Post post = new Post(requestDto, account);
        postRepository.save(post);
        PostCreateResDto postCreateResDto = new PostCreateResDto(post);
        return postCreateResDto;
    }


    //업데이트 기능
    @Transactional
//    public PostResponseDto updatePost(PostDto requestDto, Long postId, UserDetailImpl userDetails) {
    public PostResponseDto updatePost(PostDto requestDto, Long postId, Account currentAccount) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.NotFoundPost)
        );
        if (post.getAccount().getUserId().equals(currentAccount.getUserId())) {
            post.update(requestDto);
            return new PostResponseDto(post);
        } else {
            throw new CustomException(ErrorCode.NotFoundUser);
        }

    }

    // 삭제 기능
    @Transactional
    public void deletePost(Long postId, Account currentAccount) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.NotFoundPost)
        );
        if (post.getAccount().getUserId().equals(currentAccount.getUserId())) {
            postRepository.deleteById(postId);
        } else {

            throw new CustomException(ErrorCode.NotFoundUser);

        }

//    private Post checkPost(PostRepository postRepository, Long id) {
//        return postRepository.findById(id).orElseThrow(
//                () -> new RuntimeException("Not Found Post")
//        );
    }
}

