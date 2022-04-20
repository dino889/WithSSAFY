package com.ssafy.withssafy.api;

import com.ssafy.withssafy.entity.User;
import com.ssafy.withssafy.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 전체 사용자를 조회한다.
     * @return List<User>
     */
    @GetMapping()
    @ApiOperation(value = "전체 사용자를 조회한다.")
    public ResponseEntity<List<User>> findAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    /**
     * 특정 사용자를 조회한다.
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "특정 사용자를 조회한다.")
    public ResponseEntity<Optional<User>> findByUid(@PathVariable("id") Long id){
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    /**
     * 아이디와 비밀번호를 통해 유저를 조회한다.
     * @param u_id
     * @param password
     * @return
     */
    @GetMapping("/login")
    @ApiOperation(value = "ID와 Password를 통해 사용자를 조회한다. (비밀번호나 아이디가 없으면 조회X)")
    public ResponseEntity<Optional<User>> login(@RequestParam("아이디")String u_id, @RequestParam("비밀번호")String password){
        return new ResponseEntity<>(userService.login(u_id, password), HttpStatus.OK);
    }


    /**
     * 새로운 사용자를 등록한다.
     * @param user
     * @return 등록된 사용자의 정보
     */
    @PostMapping
    @ApiOperation(value = "해당 회원 정보로 가입한다.")
    public ResponseEntity<Object> save(@RequestBody User user){
        User result = userService.insertUser(user);
        if(result == null) return new ResponseEntity<>(new String("가입할 수 없는 정보 입력으로 취소되었습니다."), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(result, HttpStatus.OK);
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
    @ApiOperation(value = "해당 이메일을 가진 유저 비밀번호를 수정 후 변경된 User를 반환한다")
    public ResponseEntity<Optional<User>> update(@RequestParam(value="id")Long id, @RequestParam(value="password") String password){
        return new ResponseEntity<>(userService.updatePasswordByUid(id, password), HttpStatus.OK);
    }
}
