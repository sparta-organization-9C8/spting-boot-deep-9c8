package com.example.loginlivesession2.post.service;

import com.example.loginlivesession2.account.entity.Account;
import com.example.loginlivesession2.post.dto.PostDto;
import com.example.loginlivesession2.post.dto.PostResponseDto;
import com.example.loginlivesession2.post.entity.Post;
import com.example.loginlivesession2.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    // 모든 글 읽어오기 + 추가 예정
    @Transactional
    public List<Post> getAllpost() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    //상세조회
    @Transactional
    public PostResponseDto getOnePost(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다."));

        PostResponseDto postResponseDto = new PostResponseDto(post);

        return postResponseDto;

    }



    //글 쓰기
    @Transactional
    public Post createPost(PostDto requestDto, Account account) {

        Post Post = new Post(requestDto, account);
        return postRepository.save(Post);
    }


    //업데이트 기능
    @Transactional
    public PostResponseDto updatePost(PostDto requestDto, Long postId, Account currentAccount) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 글이 없습니다.")
        );
        if (post.getAccount().getUserId() == currentAccount.getUserId()){
            post.update(requestDto);
            return new PostResponseDto(post);
        }else {
            throw new RuntimeException("작성자가 아닙니다.");
        }

    }

    // 삭제 기능
    @Transactional
    public void deletePost(Long postId, Account currentAccount) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 글이 없습니다.")
        );
        if (post.getAccount().getUserId().equals(currentAccount.getUserId())) {
            postRepository.deleteById(postId);
        } else {
            throw new RuntimeException("작성자가 아닙니다.");
        }
    }
}

