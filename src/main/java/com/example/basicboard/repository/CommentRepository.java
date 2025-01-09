package com.example.basicboard.repository;

import com.example.basicboard.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findCommentById(Long id);

    @Query("SELECT c FROM Comment c JOIN FETCH c.post WHERE c.post.id = :id")
    List<Comment> findCommentsByPostId(@Param("id") Long id);

}
