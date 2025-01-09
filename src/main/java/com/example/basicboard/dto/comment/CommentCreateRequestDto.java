package com.example.basicboard.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CommentCreateRequestDto {

    private String content;

}
