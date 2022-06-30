package com.ssafy.withssafy.api;

import com.ssafy.withssafy.dto.sbcomment.SbCommentDto;
import com.ssafy.withssafy.dto.sbcomment.SbCommentRequest;
import com.ssafy.withssafy.service.sbcomment.SbCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Comment 관리 API
 * @author Jueun
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sb-comment")
@Api(tags = "Study 댓글 API")
public class SbCommentController {
    @Autowired
    SbCommentService sbCommentService;

    @GetMapping
    @ApiOperation(value = "모든 메세지를 조회합니다.")
    public ResponseEntity<List<SbCommentDto>> findAll(){
        return new ResponseEntity<>(sbCommentService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping
    @ApiOperation(value = "해당 아이디를 가진 댓글을 삭제합니다.")
    public ResponseEntity<Boolean> delete(@RequestParam Long id){
        return new ResponseEntity<>(sbCommentService.delete(id), HttpStatus.OK);
    }

    @PutMapping
    @ApiOperation(value = "댓글의 내용을 수정합니다. *(ID, Content 필수)")
    public ResponseEntity<SbCommentDto> update(@RequestBody SbCommentRequest sbCommentRequest){
        return new ResponseEntity<>(sbCommentService.update(sbCommentRequest),HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "댓글을 입력합니다.")
    public ResponseEntity<SbCommentDto> insert(@RequestBody SbCommentRequest sbCommentRequest){
        return new ResponseEntity<>(sbCommentService.insert(sbCommentRequest), HttpStatus.OK);
    }

    @GetMapping("{boardId}")
    @ApiOperation(value = "게시판에 해당하는 댓글을 조회합니다.")
    public ResponseEntity<List<SbCommentDto>> findByBoardId(@PathVariable Long boardId){
        return new ResponseEntity<>(sbCommentService.findByBoardId(boardId), HttpStatus.OK);
    }

    @GetMapping("user/{userId}")
    @ApiOperation(value = "사용자가 남긴 댓글을 조회합니다. *(URL 잘 보고 할 것 comment/u/{userId})")
    public ResponseEntity<List<SbCommentDto>> findByUserId(@PathVariable Long userId){
        return new ResponseEntity<>(sbCommentService.findByUserId(userId), HttpStatus.OK);
    }
}
