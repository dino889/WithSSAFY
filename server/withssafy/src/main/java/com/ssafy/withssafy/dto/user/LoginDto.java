package com.ssafy.withssafy.dto.user;

import com.ssafy.withssafy.dto.classroom.ClassRoomDto;
import com.ssafy.withssafy.dto.manager.ManagerDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginDto {
    @ApiModelProperty(example = "유저 고유 아이디")
    private Long id;
    @ApiModelProperty(example = "유저 반 아이디")
    private ClassRoomDto classRoomDto;
    @ApiModelProperty(example = "유저 계정 아이디")
    private String userId;
    @ApiModelProperty(example = "비밀번호")
    private String password;
    @ApiModelProperty(example = "계정 상태")
    private int state;
    @ApiModelProperty(example = "디바이스 토큰")
    private String deviceToken;
    @ApiModelProperty(example = "학번")
    private String studentId;
    @ApiModelProperty(example = "이름")
    private String name;
    private ManagerDto managerDto;
}
