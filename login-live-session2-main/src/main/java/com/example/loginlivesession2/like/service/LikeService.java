package com.example.loginlivesession2.like.service;

import com.example.loginlivesession2.account.entity.Account;
import com.example.loginlivesession2.like.entity.Like;
import com.example.loginlivesession2.like.repository.LikeRepository;
import com.example.loginlivesession2.post.entity.Post;
import com.example.loginlivesession2.post.repository.PostRepository;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public String createLike(Long postId, UserDetailsImpl userDetails) {
        Account account = userDetails.getAccount();

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("포스트없음"));

        if(!likeRepository.existsByPostAndAccount(post, account)) { // !true = false // 존재하지 않다면?
            Like likes = new Like(post, account);
            likeRepository.save(likes);
            return "좋아요눌러짐";
        } else {
            likeRepository.deleteByPostAndAccount_Username(post, account.getUsername());
            return "좋아요 삭제";
        }
    }
}
