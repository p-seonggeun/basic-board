package com.example.basicboard.controller;

import com.example.basicboard.dto.post.*;
import com.example.basicboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @GetMapping("/")
    public List<PostListResponseDto> getAllPost() {
        return postService.getPosts();
    }

    @GetMapping("/{id}")
    public PostDetailResponseDto getPostById(@PathVariable("id") Long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping("/")
    public PostCreateResponseDto createPost(@RequestBody PostCreateRequestDto requestDto) {
        return postService.savePost(requestDto);
    }

    @PutMapping("/{id}")
    public PostUpdateResponseDto updatePostById(@PathVariable("id") Long postId, @RequestBody PostUpdateRequestDto requestDto) {
        return postService.updatePostById(postId, requestDto);
    }

    @DeleteMapping("/{id}")
    public String deletePostById(@PathVariable("id") Long postId) {
        boolean result = postService.deletePostById(postId);
        if (!result) {
            return postId + "번 게시글 삭제 중 예외 발생";
        }
        return postId + "번 게시글 삭제 완료";
    }
}
