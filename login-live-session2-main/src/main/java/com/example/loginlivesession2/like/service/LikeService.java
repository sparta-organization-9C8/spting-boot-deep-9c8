package com.example.loginlivesession2.like.service;


import com.example.loginlivesession2.account.entity.Account;
import com.example.loginlivesession2.exception.CustomException;
import com.example.loginlivesession2.exception.ErrorCode;
import com.example.loginlivesession2.like.entity.Like;
import com.example.loginlivesession2.like.repository.LikeRepository;
import com.example.loginlivesession2.post.entity.Post;
import com.example.loginlivesession2.post.repository.PostRepository;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;


    @Transactional
    public String createLike(Long postId, UserDetailsImpl userDetails) {
        Account account = userDetails.getAccount();
        // requestDto에 있는 postId를 이용해서 post를 들고옵니다. (postRepository를 사용)
        // a게시글을 들고옴
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.NotFoundPost));

//        Account account = accountRepository.findByEmail(email)
//                .orElseThrow(() -> new IllegalArgumentException("이메일 존재하지 않음"));
//        Boolean bool = likesRepository.existsByPostAndEmail(postId, email);
        Like likes = new Like(post, account);
        if(!likeRepository.existsByPostAndAccount(post, account)){
            likeRepository.save(likes);
            return post.getPostId() + "번 게시물에 좋아요를 눌렀습니다! " + likes.getAccount().getUsername() + "님";
        }else {
            likeRepository.deleteByPostAndAccount(post, account);
            return post.getPostId() + "번 게시물에 좋아요를 취소했습니다! " + likes.getAccount().getUsername() + "님";
        }
    }
}