package com.ssafy.withssafy.service.schedule;

import com.ssafy.withssafy.dto.schedule.ScheduleDto;
import com.ssafy.withssafy.dto.schedule.ScheduleModifyDto;
import com.ssafy.withssafy.dto.schedule.ScheduleReqDto;
import com.ssafy.withssafy.entity.ClassRoom;
import com.ssafy.withssafy.entity.Schedule;
import com.ssafy.withssafy.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ModelMapper modelMapper;

    @Transactional
    public void saveSchedule(List<ScheduleReqDto> scheduleDtoList){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for(ScheduleReqDto s: scheduleDtoList){
            LocalDateTime startDate = LocalDateTime.parse(s.getStartDate(),formatter);
            LocalDateTime endDate = LocalDateTime.parse(s.getEndDate(),formatter);
            ScheduleDto scheduleDto = new ScheduleDto(s);
            scheduleDto.setStartDate(startDate);
            scheduleDto.setEndDate(endDate);
            scheduleRepository.save(modelMapper.map(scheduleDto, Schedule.class));
        }
    }
    @Transactional
    public void modifySchedule(ScheduleModifyDto scheduleModifyDto){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDate = LocalDateTime.parse(scheduleModifyDto.getStartDate(),formatter);
        LocalDateTime endDate = LocalDateTime.parse(scheduleModifyDto.getEndDate(),formatter);
        System.out.println(startDate.toString());
        ScheduleDto scheduleDto = new ScheduleDto(scheduleModifyDto);
        scheduleDto.setStartDate(startDate);
        scheduleDto.setEndDate(endDate);
        scheduleRepository.save(modelMapper.map(scheduleDto, Schedule.class));
    }

    @Transactional
    public void deleteSchedule(Long id){
        scheduleRepository.deleteById(id);
    }

    public ScheduleDto findById(Long id) {
        Schedule schedule = scheduleRepository.findById(id).get();
        return modelMapper.map(schedule, ScheduleDto.class);
    }

    public List<ScheduleDto> findMySchedule(Long id){
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime monday = today.minusDays(today.getDayOfWeek().getValue());
        List<Schedule> recruits = scheduleRepository.findAllByClassRoomIdAndStartDateBetween(id, monday, monday.plusDays(6));
        return recruits.stream().map(recruit -> modelMapper.map(recruit, ScheduleDto.class))
                .collect(Collectors.toList());
    }

    public List<ScheduleDto> findAllMySchedule(Long id){
        List<Schedule> recruits = scheduleRepository.findAllByClassRoomIdOrderByStartDate(id);
        return recruits.stream().map(recruit -> modelMapper.map(recruit, ScheduleDto.class))
                .collect(Collectors.toList());
    }

    public List<ScheduleDto> findAllSchedule(Long id){
        List<Schedule> recruits = scheduleRepository.findAllGenerationNotice(id);
        return recruits.stream().map(recruit -> modelMapper.map(recruit, ScheduleDto.class))
                .collect(Collectors.toList());
    }

    public List<ScheduleDto> findAllScheduleByDay(Long id, LocalDate date){
        List<Schedule> recruits = scheduleRepository.findAllGenerationNoticyByDay(id, date);
        return recruits.stream().map(recruit -> modelMapper.map(recruit, ScheduleDto.class))
                .collect(Collectors.toList());
    }
}
