package com.ssafy.withssafy.api;

import com.ssafy.withssafy.dto.recruit.RecruitDto;
import com.ssafy.withssafy.dto.recruit.RecruitLikeDto;
import com.ssafy.withssafy.dto.recruit.RecruitListResDto;
import com.ssafy.withssafy.entity.Recruit;
import com.ssafy.withssafy.service.recruit.RecruitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/recruit")
@Api(tags = "채용공고 API")
public class RecruitController {

    private final RecruitService recruitService;

    @PostMapping
    @ApiOperation(value = "채용 공고 추가")
    public ResponseEntity<?> insertRecruit(@RequestBody RecruitDto recruitDto){

        recruitService.saveRecruit(recruitDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PutMapping
    @ApiOperation(value = "채용 공고 수정")
    public ResponseEntity<?> updateRecruit(@RequestBody RecruitDto recruitDto){

        recruitService.saveRecruit(recruitDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "채용 공고 삭제")
    public ResponseEntity<?> deleteRecruit(@PathVariable("id") Long id){

        recruitService.deleteRecruit(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping
    @ApiOperation(value = "채용 공고 목록 조회")
    public ResponseEntity<List<RecruitListResDto>> getAllRecruit(){

        List<RecruitListResDto> recruitDtoList = recruitService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(recruitDtoList);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "채용 공고 조회")
    public ResponseEntity<RecruitDto> getRecruit(@PathVariable("id") Long id){

        RecruitDto recruitDto = recruitService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(recruitDto);
    }

    @PostMapping("/like")
    @ApiOperation(value = "채용공고 찜하기or취소")
    public ResponseEntity<?> likeRecruit(@RequestBody RecruitLikeDto recruitLikeDto){
        recruitService.doLike(recruitLikeDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/like")
    @ApiOperation(value = "채용공고 찜했는지 여부")
    public ResponseEntity<?> isLikeRecruit(@RequestParam Long recruitId, @RequestParam Long userId){
        boolean isLike = recruitService.isLike(recruitId,userId);
        return ResponseEntity.status(HttpStatus.OK).body(isLike);
    }

    @GetMapping("/like/{id}")
    @ApiOperation(value = "찜한 채용공고 목록")
    public ResponseEntity<List<RecruitListResDto>> getMyRecruitList(@PathVariable("id") Long userId){
        List<RecruitListResDto> recruitListResDtos = recruitService.GetMyRecruit(userId);
        return ResponseEntity.status(HttpStatus.OK).body(recruitListResDtos);
    }
}
