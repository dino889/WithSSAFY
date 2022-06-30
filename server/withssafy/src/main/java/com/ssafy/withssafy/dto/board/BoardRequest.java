package com.ssafy.withssafy.dto.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class BoardRequest {
    @JsonIgnore
    private Long id;
    private Long userId;
    private Long typeId;
    private String content;
    private String photoPath;
    private String title;
    private String writeDateTime;

    public void setWriteDateTime(){
        this.writeDateTime = String.valueOf(System.currentTimeMillis());
    }
}
