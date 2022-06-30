package com.ssafy.withssafy.api;

import com.ssafy.withssafy.dto.classroom.ClassRoomDto;
import com.ssafy.withssafy.service.classroom.ClassRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 반 정보 API
 * @author Jueun
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/classroom")
@Api(tags = "반정보 API")
public class ClassRoomController {
    @Autowired
    ClassRoomService classRoomService;

    @GetMapping("/all")
    @ApiOperation(value = "모든 정보를 조회합니다.")
    public ResponseEntity<List<ClassRoomDto>> findAll(){
        return new ResponseEntity<>(classRoomService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping
    @ApiOperation(value = "해당 ID를 가진 반정보를 삭제합니다.")
    public ResponseEntity<ClassRoomDto> delete(Long id){
        return new ResponseEntity<>(classRoomService.delete(id), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "반정보를 삽입합니다.")
    public ResponseEntity<ClassRoomDto> insert(@RequestBody ClassRoomDto classRoomDto){
        return new ResponseEntity<>(classRoomService.insert(classRoomDto), HttpStatus.OK);
    }

    @PutMapping
    @ApiOperation(value = "정보를 업데이트 합니다.")
    public ResponseEntity<ClassRoomDto> update(@RequestBody ClassRoomDto classRoomDto){
        return new ResponseEntity<>(classRoomService.update(classRoomDto), HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "해당 ID와 일치하는 반정보를 조회합니다.")
    public ResponseEntity<ClassRoomDto> findById(@RequestParam Long id){
        return new ResponseEntity<>(classRoomService.findById(id), HttpStatus.OK);
    }
}
