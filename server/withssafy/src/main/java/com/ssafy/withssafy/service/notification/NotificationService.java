package com.ssafy.withssafy.service.notification;

import com.ssafy.withssafy.dto.notification.NotificationResponseDto;
import com.ssafy.withssafy.dto.notification.NotificationRequestDto;

import java.util.List;

public interface NotificationService {
    List<NotificationResponseDto> findAll();
    NotificationResponseDto insert(NotificationRequestDto notificationRequestDto);
    NotificationResponseDto update(NotificationRequestDto notificationRequestDto);
    NotificationResponseDto delete(Long id);
    NotificationResponseDto findById(Long id);
    List<NotificationResponseDto> findByUserId(Long userId);
    List<NotificationResponseDto> findByUserIdAndType(Long userId, Integer type);
}
