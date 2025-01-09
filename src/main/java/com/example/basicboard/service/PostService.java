package com.example.basicboard.service;

import com.example.basicboard.dto.PostCreateRequestDto;
import com.example.basicboard.dto.PostCreateResponseDto;
import com.example.basicboard.dto.PostDetailResponseDto;
import com.example.basicboard.dto.PostUpdateResponseDto;
import com.example.basicboard.entity.Post;
import com.example.basicboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Post findPost = postRepository.findPostById(postId)
                .orElseThrow(() -> new NoSuchElementException("찾는 게시글이 없습니다."));

        /**
         * Comment 작업 후 보완 필요
         */
        return null;
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
