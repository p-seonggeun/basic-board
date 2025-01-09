package com.example.basicboard.controller;

import com.example.basicboard.dto.comment.CommentCreateRequestDto;
import com.example.basicboard.dto.comment.CommentCreateResponseDto;
import com.example.basicboard.dto.comment.CommentUpdateRequestDto;
import com.example.basicboard.dto.comment.CommentUpdateResponseDto;
import com.example.basicboard.service.CommentService;
import com.example.basicboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public CommentCreateResponseDto createComment(@PathVariable("postId") Long postId, CommentCreateRequestDto requestDto) {
        return commentService.saveComment(postId, requestDto);
    }

    @PutMapping("/{postId}/{commentId}")
    public CommentUpdateResponseDto updateCommentById(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId, CommentUpdateRequestDto requestDto) {
        return commentService.updateCommentById(commentId, requestDto);
    }

    @DeleteMapping("/{postId}/{commentId}")
    public String deleteCommentById(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) {
        boolean result = commentService.deleteCommentById(commentId);
        if (!result) {
            return postId + "번 글의 " + commentId + "번 댓글 삭제 중 예외 발생";
        }
        return postId + "번 글의 " + commentId + "번 댓글 삭제 완료";
    }
}
