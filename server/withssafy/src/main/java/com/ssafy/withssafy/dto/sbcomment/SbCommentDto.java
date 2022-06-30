package com.ssafy.withssafy.dto.sbcomment;

import com.ssafy.withssafy.dto.comment.WriterDto;
import com.ssafy.withssafy.dto.studyboard.StudyBoardResponse;
import com.ssafy.withssafy.dto.user.UserDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SbCommentDto {
    private Long id;
    private Long boardId;
    private WriterDto User;
    private Long parentId;
    private String content;
    @ApiModelProperty(hidden = true)
    private String write_dt;
}
