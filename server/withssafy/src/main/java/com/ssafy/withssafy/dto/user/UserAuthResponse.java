package com.ssafy.withssafy.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserAuthResponse {
    @ApiModelProperty(example = "유저 권한")
    private int auth;
    @ApiModelProperty(example = "유저 기본 정보")
    private UserInfo user;
}
