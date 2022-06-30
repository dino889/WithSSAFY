package com.ssafy.withssafy.dto.notice;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class NoticeReqDto {
    @ApiModelProperty(hidden = true)
    private Long id;
    private Long userId;
    private Long typeId;
    private String title;
    private String content;
    private String photoPath;
    private String filePath;
    private Long classRoomId;
    @ApiModelProperty(hidden = true)
    private LocalDateTime writeDt;
}
