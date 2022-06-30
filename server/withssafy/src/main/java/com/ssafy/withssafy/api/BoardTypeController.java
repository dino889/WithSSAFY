package com.ssafy.withssafy.api;

import com.ssafy.withssafy.dto.board.BoardTypeRequest;
import com.ssafy.withssafy.service.board.BoardTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board-types")
public class BoardTypeController {

    private final BoardTypeService boardTypeService;

    @GetMapping()
    public ResponseEntity<?> getBoardTypes() {
        return new ResponseEntity<>(boardTypeService.getBoardTypes(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> addBoardType(@RequestBody BoardTypeRequest boardTypeRequest){
        boardTypeService.addBoardType(boardTypeRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifyBoardTypeById(@RequestBody BoardTypeRequest boardTypeRequest, @PathVariable("id") Long id){
        boardTypeService.modifyBoardTypeById(boardTypeRequest, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeBoardTypeById(@PathVariable("id") Long id){
        boardTypeService.removeBoardTypeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
