package com.ssafy.withssafy.dto.schedule;

import com.ssafy.withssafy.entity.ClassRoom;
import com.ssafy.withssafy.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
public class ScheduleDto {
    private Long id;
    private Long userId;
    private Long classRoomId;
    @ApiModelProperty(example = "yyyy-mm-ddThh:mm:ss")
    private LocalDateTime endDate;
    private String title;
    private String memo;
    @ApiModelProperty(example = "yyyy-mm-ddThh:mm:ss")
    private LocalDateTime startDate;
    private int weeks;

    public ScheduleDto(ScheduleReqDto scheduleReqDto){
        this.id = scheduleReqDto.getId();
        this.userId = scheduleReqDto.getUserId();
        this.classRoomId = scheduleReqDto.getClassRoomId();
        this.title = scheduleReqDto.getTitle();
        this.memo = scheduleReqDto.getMemo();
        this.weeks = scheduleReqDto.getWeeks();
    }

    public ScheduleDto(ScheduleModifyDto scheduleModifyDto){
        this.id = scheduleModifyDto.getId();
        this.userId = scheduleModifyDto.getUserId();
        this.classRoomId = scheduleModifyDto.getClassRoomId();
        this.title = scheduleModifyDto.getTitle();
        this.memo = scheduleModifyDto.getMemo();
        this.weeks = scheduleModifyDto.getWeeks();
    }
}
