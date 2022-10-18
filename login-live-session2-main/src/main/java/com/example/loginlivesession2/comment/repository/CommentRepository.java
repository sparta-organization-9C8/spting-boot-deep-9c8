package com.example.loginlivesession2.comment.repository;

import com.example.loginlivesession2.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByOrderByCreatedAtDesc();
//    Optional<Comment> findById(Long id);

}