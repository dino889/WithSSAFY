package com.ssafy.withssafy.api;

import com.ssafy.withssafy.dto.notice.NoticeModifyReqDto;
import com.ssafy.withssafy.dto.notice.NoticeReqDto;
import com.ssafy.withssafy.dto.notice.NoticeResDto;
import com.ssafy.withssafy.dto.notice.NoticeTypeDto;
import com.ssafy.withssafy.service.Notice.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
@Api(tags = "공지사항 API")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping()
    @ApiOperation(value = "공지사항 목록 조회")
    public ResponseEntity<List<NoticeResDto>> getNoticess(@RequestParam Long classRoomId) {
        return new ResponseEntity<>(noticeService.getNotices(classRoomId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "공지사항 조회")
    public ResponseEntity<NoticeResDto> getBoardById(@PathVariable("id") Long id) {
        return new ResponseEntity<NoticeResDto>(noticeService.getNoticeById(id), HttpStatus.OK);
    }

    @PostMapping()
    @ApiOperation(value = "공지사항 추가", notes = "classRoomId에 6기 전체, 6기 구미 전체 등 선택해서 사용")
    public ResponseEntity<?> addBoard(@RequestBody NoticeReqDto noticeReqDto) {
        noticeService.addNotice(noticeReqDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "공지사항 수정")
    public ResponseEntity<?> modifyNoticeById(@RequestBody NoticeModifyReqDto noticeReqDto, @PathVariable("id") Long id) {
        noticeService.modifyNoticeById(noticeReqDto, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "공지사항 삭제")
    public ResponseEntity<?> removeBoardById(@PathVariable("id") Long id) {
        noticeService.removeNoticeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/type")
    @ApiOperation(value = "공지사항 타입 리스트")
    public ResponseEntity<List<NoticeTypeDto>> getBoardTypes(){
        List<NoticeTypeDto> noticeTypes = noticeService.getNoticeTypes();
        return new ResponseEntity<List<NoticeTypeDto>>(noticeTypes, HttpStatus.OK);
    }
}
