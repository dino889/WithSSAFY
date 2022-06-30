package com.ssafy.withssafy.service.classroom;

import com.ssafy.withssafy.dto.classroom.ClassRoomDto;
import com.ssafy.withssafy.entity.ClassRoom;

import java.util.List;

public interface ClassRoomService {
    List<ClassRoomDto> findAll();
    ClassRoomDto update(ClassRoomDto classRoomDto);
    ClassRoomDto delete(Long id);
    ClassRoomDto insert(ClassRoomDto classRoomDto);
    ClassRoomDto findById(Long id);
}
