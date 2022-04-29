package com.ssafy.withssafy.dto.studyboard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StudyBoardRequest {
    private Long id;

    private Long userId;

    private String title;

    private String content;

    private String category;

    private String area;

    private int sbLimit;

    private String writeDateTime;

    private String photoPath;

    private byte isOuting;
}
