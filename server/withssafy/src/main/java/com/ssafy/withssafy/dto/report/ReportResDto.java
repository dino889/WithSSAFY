package com.ssafy.withssafy.dto.report;

import com.ssafy.withssafy.dto.board.BoardResponse;
import com.ssafy.withssafy.dto.board.BoardTypeDto;
import com.ssafy.withssafy.dto.comment.CommentDto;
import com.ssafy.withssafy.dto.user.UserDto;
import com.ssafy.withssafy.entity.Comment;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ReportResDto {
    private Long id;
    private BoardResponse board;
    private CommentDto comment;
    private UserDto user;
    private String content;
    private String write_dt;
}
