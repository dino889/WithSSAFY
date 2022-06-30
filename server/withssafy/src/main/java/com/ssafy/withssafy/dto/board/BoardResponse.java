package com.ssafy.withssafy.dto.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class BoardResponse {
    private Long id;
    private WriterDto user;
    private BoardTypeDto boardType;
    private String content;
    private String photoPath;
    private String title;
    private String writeDateTime;
    private CommentDto[] comments;
    private Integer commentCount;
    @JsonIgnore
    private LikeDto[] likes;
    private Integer likeCount;
}
