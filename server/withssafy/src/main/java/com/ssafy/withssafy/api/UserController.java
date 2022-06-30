package com.ssafy.withssafy.api;

import com.google.firebase.remoteconfig.internal.TemplateResponse;
import com.ssafy.withssafy.dto.user.LoginDto;
import com.ssafy.withssafy.dto.user.UserDto;
import com.ssafy.withssafy.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User 관리 API
 * @author Jueun
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Api(tags = "유저 API")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 전체 사용자를 조회한다.
     * @return List<User>
     */
    @GetMapping()
    @ApiOperation(value = "전체 사용자를 조회한다.")
    public ResponseEntity<List<UserDto>> findAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    /**
     * 특정 사용자를 조회한다.
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "특정 사용자를 조회한다.")
    public ResponseEntity<UserDto> findByUid(@PathVariable("id") Long id){
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    /**
     * 아이디와 비밀번호를 통해 유저를 조회한다.
     * @param u_id
     * @param password
     * @return
     */
    @GetMapping("/login")
    @ApiOperation(value = "ID와 Password를 통해 사용자를 조회한다. (비밀번호나 아이디가 없으면 500 ERROR)")
    public ResponseEntity<LoginDto> login(@RequestParam("아이디")String u_id, @RequestParam("비밀번호")String password){
        return new ResponseEntity<>(userService.login(u_id, password), HttpStatus.OK);
    }


    /**
     * 새로운 사용자를 등록한다.
     * @param userDto
     * @return 등록된 사용자의 정보
     */
    @PostMapping
    @ApiOperation(value = "해당 회원 정보로 가입한다.")
    public ResponseEntity<UserDto> join(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.insertUser(userDto), HttpStatus.OK);
    }

    /**
     * 유저 ID를 통해 사용자를 삭제한다.
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "해당 아이디를 가진 유저를 삭제한다.", response = Boolean.class)
    public ResponseEntity<Boolean> delete(@RequestParam(value="아이디")Long id){
        return new ResponseEntity<>(userService.deleteByUid(id), HttpStatus.OK);
    }

    /**
     * 해당 ID를 가진 유저 비밀번호를 수정한다.
     * @param id, password
     * @return 변경된 User 정보
     */
    @PatchMapping
    @ApiOperation(value = "해당 아이디를 가진 유저 비밀번호를 수정 후 변경된 User를 반환한다")
    public ResponseEntity<UserDto> update(@RequestParam(value="아이디")Long id, @RequestParam(value="비밀번호") String password){
        return new ResponseEntity<>(userService.updatePasswordByUid(id, password), HttpStatus.OK);
    }

    @PatchMapping("/class")
    @ApiOperation(value = "해당 아이디를 가진 유저 반정보를 수정 후 변경된 User를 반환한다. *(URL /class 주의)")
    public ResponseEntity<UserDto> updateClass(@RequestParam Long id, @RequestParam Long classId){
        return new ResponseEntity<>(userService.updateClassById(id, classId), HttpStatus.OK);
    }


    /*--------------------- 매니저 API --------------------*/
    @PostMapping("/manager")
    @ApiOperation(value = "해당 유저 정보를 관리자로 가입한다. (status = 0:그냥 매니저, 1:컨설턴트, 2:프로")
    public ResponseEntity<LoginDto> insertManager(@RequestBody UserDto userDto, @RequestParam int status){
        return new ResponseEntity<>(userService.insertManager(userDto, status), HttpStatus.OK);
    }

    @PatchMapping("/manager/state")
    @ApiOperation(value = "유저의 state를 변경합니다.")
    public ResponseEntity<UserDto> updateState(@RequestParam Long id, @RequestParam int state){
        return new ResponseEntity<>(userService.updateState(id, state), HttpStatus.OK);
    }

    @PatchMapping("manager/update-device-token")
    @ApiOperation(value = "유저의 Device Token을 변경합니다.")
    public ResponseEntity<UserDto> updateDeviceToken(@RequestParam Long id, @RequestParam String token){
        return new ResponseEntity<>(userService.updateDeviceToken(id, token), HttpStatus.OK);
    }

    @GetMapping("/manager/user-state-zero")
    @ApiOperation(value = "state가 0인 학생을 조회합니다.")
    public ResponseEntity<List<UserDto>> getUserStateZero(){
        return new ResponseEntity<>(userService.findStateZero(), HttpStatus.OK);
    }

    @GetMapping("/manager/{userId}")
    @ApiOperation(value = "유저의 정보와 권한")
    public ResponseEntity<?> getAuthByUserId(@PathVariable(value = "userId") Long userId){
        return new ResponseEntity<>(userService.findAuthByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/token/{token}")
    @ApiOperation(value = "Device Token으로 유저 찾기")
    public ResponseEntity<UserDto> getUserByDeviceToken(@PathVariable(value = "token")String token){
        return new ResponseEntity<>(userService.findByDeviceToken(token), HttpStatus.OK);
    }
}
