package com.example.basicboard.service;

import com.example.basicboard.dto.*;
import com.example.basicboard.entity.Comment;
import com.example.basicboard.entity.Post;
import com.example.basicboard.repository.CommentRepository;
import com.example.basicboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentCreateResponseDto saveComment(Long postId, CommentCreateRequestDto requestDto) {
        Post findPost = postRepository.findPostById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Comment comment = new Comment(requestDto.getContent());
        comment.addComment(findPost);

        Comment savedComment = commentRepository.save(comment);

        return CommentCreateResponseDto.builder()
                .id(savedComment.getId())
                .postId(savedComment.getPost().getId())
                .content(savedComment.getContent())
                .createdAt(savedComment.getCreatedAt())
                .updatedAt(savedComment.getUpdatedAt())
                .build();
    }

    public List<CommentResponseDto> getCommentsByPostId(Long postId) {
        List<Comment> commentsByPostId = commentRepository.findCommentsByPostId(postId);
        List<CommentResponseDto> result = new ArrayList<>();

        for (Comment comment : commentsByPostId) {
            result.add(CommentResponseDto.builder()
                    .id(comment.getId())
                    .postId(comment.getPost().getId())
                    .content(comment.getContent())
                    .createdAt(comment.getCreatedAt())
                    .updatedAt(comment.getUpdatedAt())
                    .build());
        }

        return result;
    }

    @Transactional
    public CommentUpdateResponseDto updateCommentById(Long commentId, CommentUpdateRequestDto requestDto) {
        Comment findComment = commentRepository.findCommentById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        findComment.changeContent(requestDto.getContent());

        return CommentUpdateResponseDto.builder()
                .id(findComment.getId())
                .postId(findComment.getPost().getId())
                .content(findComment.getContent())
                .createdAt(findComment.getCreatedAt())
                .updatedAt(findComment.getUpdatedAt())
                .build();
    }

    @Transactional
    public boolean deleteCommentById(Long commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
            return !commentRepository.existsById(commentId);
        }
        throw new IllegalArgumentException("삭제할 댓글이 존재하지 않습니다.");
    }
}
