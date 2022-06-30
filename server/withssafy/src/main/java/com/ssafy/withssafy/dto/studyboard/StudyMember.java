package com.ssafy.withssafy.dto.studyboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StudyMember {
    @JsonProperty(value = "id")
    private Long userId;
    @JsonProperty(value = "name")
    private String userName;
}
