package com.ssafy.withssafy.dto.comment;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CommentDto {
    private Long id;
    private Long boardId;
    private Long userId;
    private Long parentId;
    private String content;
    private String write_dt;

    public void setWriteDateTime(){
        this.write_dt = String.valueOf(System.currentTimeMillis());
    }
}
