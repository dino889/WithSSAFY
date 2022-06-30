package com.ssafy.withssafy.api;

import com.ssafy.withssafy.dto.report.ReportReqDto;
import com.ssafy.withssafy.dto.report.ReportResDto;
import com.ssafy.withssafy.service.report.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 신고 관리 API
 * @author Jueun
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
@Api(tags = "신고 API")
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    @ApiOperation(value = "전체 신고 내용을 조회한다.")
    public ResponseEntity<List<ReportResDto>> findAll(){
        return new ResponseEntity<>(reportService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "신고를 접수합니다.")
    public ResponseEntity<List<ReportResDto>> insert(@RequestBody ReportReqDto reportDto){
        return new ResponseEntity<>(reportService.insert(reportDto), HttpStatus.OK);
    }

    @DeleteMapping
    @ApiOperation(value = "신고를 철회합니다.")
    public ResponseEntity<ReportResDto> delete(@RequestParam Long id){
        return new ResponseEntity<>(reportService.delete(id),HttpStatus.OK);
    }

    @GetMapping("/board")
    @ApiOperation(value = "board를 신고한 유저 목록을 불러온다")
    public ResponseEntity<List<Long>> getUserIdByBoardId(@RequestParam Long boardId){
        return new ResponseEntity<>(reportService.findByBoardId(boardId), HttpStatus.OK);
    }

    @GetMapping("comment")
    @ApiOperation(value = "comment를 신고한 유저 목록을 불러온다")
    public ResponseEntity<List<Long>> getUserIdByCommentId(@RequestParam Long commentId){
        return new ResponseEntity<>(reportService.findByCommentId(commentId), HttpStatus.OK);
    }
}
