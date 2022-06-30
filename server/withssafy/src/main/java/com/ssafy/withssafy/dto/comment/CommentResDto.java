package com.ssafy.withssafy.dto.comment;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CommentResDto {
    private Long id;
    private Long boardId;
    private WriterDto User;
    private Long parentId;
    private String content;
    private String write_dt;
}