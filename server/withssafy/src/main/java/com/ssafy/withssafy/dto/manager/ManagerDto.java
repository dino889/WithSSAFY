package com.ssafy.withssafy.dto.manager;

import com.ssafy.withssafy.dto.user.UserDto;
import com.ssafy.withssafy.entity.ClassManager;
import com.ssafy.withssafy.entity.User;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
public class ManagerDto {
    Long id;
    int auth;
    Long userId;
}
