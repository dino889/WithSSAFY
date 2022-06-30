package com.ssafy.withssafy.dto.sbcomment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.withssafy.dto.studyboard.StudyBoardResponse;
import com.ssafy.withssafy.dto.user.UserDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SbCommentRequest {
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
