package com.ssafy.withssafy.dto.notice;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class NoticeResDto {
    private Long id;
    private Long userId;
    private Long typeId;
    private String title;
    private String content;
    private String photoPath;
    private String filePath;
    private String classRoomId;
    private LocalDateTime writeDt;
}
