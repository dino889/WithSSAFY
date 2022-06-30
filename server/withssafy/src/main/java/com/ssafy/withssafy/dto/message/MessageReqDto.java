package com.ssafy.withssafy.dto.message;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class MessageReqDto {
    @ApiModelProperty(hidden = true)
    public Long id;
    @ApiModelProperty(example = "받는 유저 아이디")
    public Long u_toId;
    @ApiModelProperty(example = "보내는 유저 아이디")
    public Long u_fromId;
    @ApiModelProperty(example = "내용")
    public String content;
    @ApiModelProperty(hidden = true)
    public LocalDateTime send_dt;
}
