package com.ssafy.withssafy.dto.notification;

import com.ssafy.withssafy.dto.user.UserDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NotificationRequestDto {
    private Long id;
    private Long user;
    private int type;
    private String dateTime;
    private String title;
    private String content;

    public void setWriteDateTime(){
        this.dateTime = String.valueOf(System.currentTimeMillis());
    }
}
