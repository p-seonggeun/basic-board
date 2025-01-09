package com.example.basicboard.service;

import com.example.basicboard.dto.comment.CommentResponseDto;
import com.example.basicboard.dto.post.PostCreateRequestDto;
import com.example.basicboard.dto.post.PostCreateResponseDto;
import com.example.basicboard.dto.post.PostDetailResponseDto;
import com.example.basicboard.dto.post.PostUpdateResponseDto;
import com.example.basicboard.entity.Comment;
import com.example.basicboard.entity.Post;
import com.example.basicboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostCreateResponseDto savePost(PostCreateRequestDto requestDto) {
        Post post = new Post(requestDto.getTitle(), requestDto.getContent());
        Post savedPost = postRepository.save(post);

        return PostCreateResponseDto.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .createdAt(savedPost.getCreatedAt())
                .updatedAt(savedPost.getUpdatedAt())
                .build();
    }

    public PostDetailResponseDto getPostById(Long postId) {
        Post findPost = postRepository.findPostWithCommentsById(postId)
                .orElseThrow(() -> new NoSuchElementException("찾는 게시글이 없습니다."));

        List<Comment> findComments = findPost.getComments();
        List<CommentResponseDto> commentDtos = new ArrayList<>();
        for (Comment comment : findComments) {
            commentDtos.add(CommentResponseDto.builder()
                    .id(comment.getId())
                    .postId(comment.getId())
                    .content(comment.getContent())
                    .createdAt(comment.getCreatedAt())
                    .updatedAt(comment.getUpdatedAt())
                    .build());
        }

        return PostDetailResponseDto.builder()
                .id(findPost.getId())
                .title(findPost.getTitle())
                .content(findPost.getContent())
                .comments(commentDtos)
                .createdAt(findPost.getCreatedAt())
                .updatedAt(findPost.getUpdatedAt())
                .build();
    }

    @Transactional
    public PostUpdateResponseDto updatePostById(Long postId, PostCreateRequestDto requestDto) {
        Post findPost = postRepository.findPostById(postId)
                .orElseThrow(() -> new IllegalArgumentException("찾는 게시글이 존재하지 않습니다."));

        findPost.changePost(requestDto.getTitle(), requestDto.getContent());

        return PostUpdateResponseDto.builder()
                .id(findPost.getId())
                .title(findPost.getTitle())
                .content(findPost.getContent())
                .createdAt(findPost.getCreatedAt())
                .updatedAt(findPost.getUpdatedAt())
                .build();
    }

    @Transactional
    public boolean deletePostById(Long postId) {
        if (postRepository.existsById(postId)) {
            postRepository.deleteById(postId);
            return !postRepository.existsById(postId);
        }
        throw new IllegalArgumentException("삭제할 게시글이 존재하지 않습니다.");
    }
}
