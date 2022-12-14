package com.example.loginlivesession2.like.repository;

import com.example.loginlivesession2.account.entity.Account;
import com.example.loginlivesession2.like.entity.Like;
import com.example.loginlivesession2.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    //    List<Likes> findByUserEmailAndPost(String email, Long post);
//
    Boolean existsByPostAndAccount(Post post, Account account);
    void deleteByPostAndAccount(Post post, Account account);
}