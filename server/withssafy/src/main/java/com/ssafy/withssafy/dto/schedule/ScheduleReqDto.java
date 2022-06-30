package com.ssafy.withssafy.dto.schedule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ScheduleReqDto {
    @ApiModelProperty(hidden = true)
    private Long id;
    private Long userId;
    private Long classRoomId;
    @ApiModelProperty(example = "yyyy-MM-dd hh:mm")
    private String endDate;
    private String title;
    private String memo;
    @ApiModelProperty(example = "yyyy-MM-dd hh:mm")
    private String startDate;
    private int weeks;
}
