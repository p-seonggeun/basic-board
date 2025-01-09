package com.example.basicboard.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PostCreateRequestDto {

    private String title;
    private String content;

}
