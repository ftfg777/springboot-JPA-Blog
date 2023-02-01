package com.jcw.blog.dto;

import lombok.Data;

@Data
public class ReplySaveRequestDto {
    private Long userId;
    private Long boardId;
    private String content;
}
