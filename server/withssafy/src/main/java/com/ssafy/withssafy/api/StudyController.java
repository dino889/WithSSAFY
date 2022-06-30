package com.ssafy.withssafy.api;

import com.ssafy.withssafy.dto.studyboard.StudyBoardRequest;
import com.ssafy.withssafy.dto.studyboard.StudyBoardResponse;
import com.ssafy.withssafy.dto.studyboard.StudyMemberRequest;
import com.ssafy.withssafy.service.studyboard.StudyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study-boards")
@Api(tags = "스터디 관련 API")
public class StudyController {

    private final StudyService studyService;

    @GetMapping()
    @ApiOperation(value = "스터디 전체 조회")
    public ResponseEntity<List<StudyBoardResponse>> getStudyBoards() {
        List<StudyBoardResponse> studyBoardResponses = studyService.getStudyBoards();
        return new ResponseEntity<>(studyBoardResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "id 기반 스터디 조회")
    public ResponseEntity<StudyBoardResponse> getStudyBoardById(@PathVariable("id") Long id) {
        StudyBoardResponse studyBoardResponse = studyService.getStudyBoardById(id);
        return new ResponseEntity<>(studyBoardResponse, HttpStatus.OK);
    }

    @PostMapping()
    @ApiOperation(value = "스터디 등록")
    public ResponseEntity<?> addStudyBoard(@RequestBody StudyBoardRequest studyBoardRequest) {
        studyService.addStudyBoard(studyBoardRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "스터디 수정")
    public ResponseEntity<?> modifyStudyBoard(@PathVariable("id") Long id, @RequestBody StudyBoardRequest studyBoardRequest) {
        studyService.modifyStudyBoard(id, studyBoardRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "스터디 제거")
    public ResponseEntity<?> removeStudyBoardById(@PathVariable("id") Long id) {
        studyService.removeStudyBoardById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/join")
    @ApiOperation(value = "스터디 참가")
    public ResponseEntity<?> joinStudy(@PathVariable("id") Long id, @RequestBody StudyMemberRequest studyMemberRequest) {
        studyService.joinStudy(id, studyMemberRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}/leave")
    @ApiOperation(value = "스터디 탈퇴")
    public ResponseEntity<?> leaveStudy(@PathVariable("id") Long id, @RequestBody StudyMemberRequest studyMemberRequest) {
        studyService.leaveStudy(id, studyMemberRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/study")
    @ApiOperation(value = "스터디만 조회")
    public ResponseEntity<List<StudyBoardResponse>> findByType(){
        return new ResponseEntity<>(studyService.findByType(0), HttpStatus.OK);
    }

    @GetMapping("/team-building")
    @ApiOperation(value = "팀빌딩만 조회")
    public ResponseEntity<List<StudyBoardResponse>> findByArea(@RequestParam Long classRoomId){
        return new ResponseEntity<>(studyService.findByArea(classRoomId), HttpStatus.OK);
    }
}
