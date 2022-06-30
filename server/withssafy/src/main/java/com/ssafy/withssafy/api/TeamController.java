package com.ssafy.withssafy.api;

import com.ssafy.withssafy.dto.Team.TeamDto;
import com.ssafy.withssafy.service.team.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
@Api(tags = "팀DB API")
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    @ApiOperation(value = "팀 정보를 모두 불러온다.")
    public ResponseEntity<List<TeamDto>> findAll(){
        return new ResponseEntity<>(teamService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/last")
    @ApiOperation(value = "팀 정보 마지막 컬럼을 가져온다.")
    public ResponseEntity<TeamDto> findLast(){
        return new ResponseEntity<>(teamService.findLast(), HttpStatus.OK);
    }

    @PutMapping
    @ApiOperation(value = "팀 정보를 입력한다.")
    public ResponseEntity<TeamDto> insert(@RequestBody TeamDto teamDto){
        return new ResponseEntity<>(teamService.insert(teamDto), HttpStatus.OK);
    }
}
