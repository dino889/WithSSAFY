package com.ssafy.withssafy.dto.studyboard;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StudyBoardResponse {
    private Long id;

    private WriterDto user;

    private String title;

    private String content;

    private String category;

    private String area;

    private int sbLimit;

    private String writeDateTime;

    private String photoPath;

    private int isOuting;

    private StudyMember[] studyMembers;

    private int type;
}
