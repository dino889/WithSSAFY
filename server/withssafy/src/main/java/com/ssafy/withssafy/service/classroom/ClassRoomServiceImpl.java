package com.ssafy.withssafy.service.classroom;

import com.ssafy.withssafy.dto.classroom.ClassRoomDto;
import com.ssafy.withssafy.entity.ClassRoom;
import com.ssafy.withssafy.repository.ClassRoomRepository;
import org.hibernate.annotations.OnDelete;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassRoomServiceImpl implements ClassRoomService{

    @Autowired
    ClassRoomRepository classRoomRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<ClassRoomDto> findAll() {
        return classRoomRepository.findAll().stream().map(classRoom -> modelMapper.map(classRoom, ClassRoomDto.class)).collect(Collectors.toList());
    }

    @Override
    public ClassRoomDto update(ClassRoomDto classRoomDto) {
        classRoomRepository.update(classRoomDto.getId(), classRoomDto.getGeneration(), classRoomDto.getArea(), classRoomDto.getClassDescription());
        return findById(classRoomDto.getId());
    }

    @Override
    @Modifying(clearAutomatically = true)
    public ClassRoomDto delete(Long id) {
        Optional<ClassRoom> classRoom = classRoomRepository.findById(id);
        if(!classRoom.isPresent()) return null;

        classRoomRepository.delete(classRoom.get());
        return modelMapper.map(classRoom, ClassRoomDto.class);
    }

    @Override
    public ClassRoomDto insert(ClassRoomDto classRoomDto) {
        ClassRoom result = classRoomRepository.save(modelMapper.map(classRoomDto, ClassRoom.class));
        return modelMapper.map(result, ClassRoomDto.class);
    }

    @Override
    public ClassRoomDto findById(Long id) {
        return modelMapper.map(classRoomRepository.findById(id).get(),ClassRoomDto.class);
    }
}
