package com.ssafy.withssafy.dto.classroom;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ClassRoomDto {
    @ApiModelProperty(example = "반 아이디")
    Long id;

    @ApiModelProperty(example = "기수")
    int generation;

    @ApiModelProperty(example = "지역")
    String area;

    @ApiModelProperty(example = "반 정보")
    String classDescription;
}
