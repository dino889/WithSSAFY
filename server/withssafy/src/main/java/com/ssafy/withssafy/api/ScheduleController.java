package com.ssafy.withssafy.api;

import com.ssafy.withssafy.dto.schedule.ScheduleDto;
import com.ssafy.withssafy.dto.schedule.ScheduleModifyDto;
import com.ssafy.withssafy.dto.schedule.ScheduleReqDto;
import com.ssafy.withssafy.entity.Schedule;
import com.ssafy.withssafy.service.schedule.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Example;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
@Api(tags = "스케줄 API")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    @ApiOperation(value = "일정 추가")
    public ResponseEntity<?> insertSchedule(@RequestBody List<ScheduleReqDto> scheduleReqDtoList){
        scheduleService.saveSchedule(scheduleReqDtoList);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PutMapping
    @ApiOperation(value = "일정 수정")
    public ResponseEntity<?> updateSchedule(@RequestBody ScheduleModifyDto scheduleModifyDto){
        scheduleService.modifySchedule(scheduleModifyDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "일정 삭제")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id){
        scheduleService.deleteSchedule(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "일정 조회")
    public ResponseEntity<ScheduleDto> getSchedule(@PathVariable Long id){
        ScheduleDto scheduleDto = scheduleService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(scheduleDto);
    }

    @GetMapping("/myclass/{id}")
    @ApiOperation(value = "우리반 이번주 일정 조회")
    public ResponseEntity<List<ScheduleDto>> getMyClassSchedule(@ApiParam("반 id") @PathVariable Long id){
        List<ScheduleDto> scheduleDtoList = scheduleService.findMySchedule(id);
        return ResponseEntity.status(HttpStatus.OK).body(scheduleDtoList);
    }

    @GetMapping("/myclass/all/{id}")
    @ApiOperation(value = "우리반 모든 일정 조회")
    public ResponseEntity<List<ScheduleDto>> getAllMyClassSchedule(@ApiParam("반 id") @PathVariable Long id){
        List<ScheduleDto> scheduleDtoList = scheduleService.findAllMySchedule(id);
        return ResponseEntity.status(HttpStatus.OK).body(scheduleDtoList);
    }

    @GetMapping("/all/{id}")
    @ApiOperation(value = "내 기수 전체 일정 조회")
    public ResponseEntity<List<ScheduleDto>> getAllSchedule(@ApiParam("반 id") @PathVariable Long id){
        List<ScheduleDto> scheduleDtoList = scheduleService.findAllSchedule(id);
        return ResponseEntity.status(HttpStatus.OK).body(scheduleDtoList);
    }

    @GetMapping("/all/day/{id}")
    @ApiOperation(value = "내 기수 전체 일정 특정 날짜 조회")
    public ResponseEntity<List<ScheduleDto>> getAllScheduleByDay(@ApiParam("반 id") @PathVariable Long id,
                                                                 @ApiParam("yyyy-MM-dd") @RequestParam String day){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(day,formatter);
        List<ScheduleDto> scheduleDtoList = scheduleService.findAllScheduleByDay(id, date);
        return ResponseEntity.status(HttpStatus.OK).body(scheduleDtoList);
    }
}
