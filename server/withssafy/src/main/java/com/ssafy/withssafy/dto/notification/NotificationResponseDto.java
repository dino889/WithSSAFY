package com.ssafy.withssafy.dto.notification;

import com.ssafy.withssafy.dto.user.UserDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NotificationResponseDto {
    private Long id;
    private UserDto user;
    private int type;
    private String dateTime;
    private String title;
    private String content;
}
