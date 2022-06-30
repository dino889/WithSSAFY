package com.ssafy.withssafy.dto.board;

import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private Long userId;
    private Long parentId;
    private String content;
}
