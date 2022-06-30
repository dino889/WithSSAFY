package com.ssafy.withssafy.api;

import com.ssafy.withssafy.dto.board.BoardRequest;
import com.ssafy.withssafy.dto.board.BoardResponse;
import com.ssafy.withssafy.dto.board.LikeDto;
import com.ssafy.withssafy.service.board.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
@Api(tags = "게시물 API")
public class BoardController {

    private final BoardService boardService;

    @GetMapping()
    @ApiOperation(value = "게시물 전체 조회")
    public ResponseEntity<List<BoardResponse>> getBoards() {
        return new ResponseEntity<>(boardService.getBoards(), HttpStatus.OK);
    }

    @GetMapping("/q")
    @ApiOperation(value = "게시물 필터 후 조회")
    public ResponseEntity<List<BoardResponse>> getBoardsByType(
            @RequestParam(value = "type", required = false) Long typeId
            , @RequestParam(value = "UID", required = false) Long userId) {
        return new ResponseEntity<>(boardService.getBoardsByFilter(typeId, userId), HttpStatus.OK);
    }

    @GetMapping("/hot-board")
    @ApiOperation(value = "좋아요 10개 이상 (핫 게시물) 게시물 조회")
    public ResponseEntity<List<BoardResponse>> getHotBoards() {
        return new ResponseEntity<>(boardService.getHotBoards(), HttpStatus.OK);
    }

    @GetMapping("/liked-board")
    @ApiOperation(value = "사용자가 좋아요 누른 게시물 조회")
    public ResponseEntity<List<BoardResponse>> getLikedBoards(@RequestParam(value = "uid") Long userId) {
        return new ResponseEntity<>(boardService.getLikedBoards(userId), HttpStatus.OK);
    }

    @GetMapping("/comment/{id}")
    @ApiOperation(value = "사용자가 댓글 단 게시물 조회")
    public ResponseEntity<?> getBoardsByComment(@PathVariable(value = "id") Long userId) {
        return new ResponseEntity<>(boardService.getBoardByComment(userId), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "id로 게시물 조회")
    public ResponseEntity<BoardResponse> getBoardById(@PathVariable("id") Long id) {
        return new ResponseEntity<BoardResponse>(boardService.getBoardById(id), HttpStatus.OK);
    }

    @PostMapping()
    @ApiOperation(value = "게시물 등록")
    public ResponseEntity<?> addBoard(@RequestBody BoardRequest boardRequest) {
        boardService.addBoard(boardRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "게시물 수정")
    public ResponseEntity<?> modifyBoardById(@RequestBody BoardRequest boardRequest, @PathVariable("id") Long id) {
        boardService.modifyBoardById(boardRequest, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "게시물 제거")
    public ResponseEntity<?> removeBoardById(@PathVariable("id") Long id) {
        boardService.removeBoardById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/like")
    @ApiOperation(value = "게시물 좋아요 or 취소")
    public ResponseEntity<?> likeRecruit(@RequestBody LikeDto likeDto) {
        boardService.doLike(likeDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/like")
    @ApiOperation(value = "게시물 좋아요했는지 여부")
    public ResponseEntity<?> isLikeRecruit(@RequestParam Long boardId, @RequestParam Long userId) {
        boolean isLike = boardService.isLike(boardId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(isLike);
    }
}
