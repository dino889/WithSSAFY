package com.ssafy.withssafy.dto.notice;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NoticeModifyReqDto {
    private String title;
    private String content;
    private String photoPath;
    private String filePath;
}
