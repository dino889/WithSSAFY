package com.ssafy.withssafy.service.notification;

import com.ssafy.withssafy.dto.notification.NotificationResponseDto;
import com.ssafy.withssafy.dto.notification.NotificationRequestDto;
import com.ssafy.withssafy.dto.user.UserDto;
import com.ssafy.withssafy.entity.Notification;
import com.ssafy.withssafy.errorcode.ErrorCode;
import com.ssafy.withssafy.exception.InvalidRequestException;
import com.ssafy.withssafy.repository.NotificationRepository;
import com.ssafy.withssafy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<NotificationResponseDto> findAll() {
        return notificationRepository.findAllOrderByDateTimeDesc().stream().map(notification -> modelMapper.map(notification, NotificationResponseDto.class)).collect(Collectors.toList());
    }

    @Override
    public NotificationResponseDto insert(NotificationRequestDto notificationRequestDto) {
        notificationRequestDto.setWriteDateTime();
        Notification notification = modelMapper.map(notificationRequestDto, Notification.class);
        notification.setUser(userRepository.findById(notificationRequestDto.getUser()).get());
        return modelMapper.map(notificationRepository.save(notification), NotificationResponseDto.class);
    }

    @Override
    @Transactional
    public NotificationResponseDto update(NotificationRequestDto notificationRequestDto) {
        if(!notificationRepository.findById(notificationRequestDto.getId()).isPresent()) return null;
        notificationRepository.update(notificationRequestDto.getId(), notificationRequestDto.getContent());
        Notification notification = notificationRepository.findById(notificationRequestDto.getId()).get();
        NotificationResponseDto result = modelMapper.map(notification, NotificationResponseDto.class);
        result.setUser(modelMapper.map(notification.getUser(), UserDto.class));
        return result;
    }

    @Override
    public NotificationResponseDto delete(Long id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        if(notification.isPresent()) {
            Notification result = notification.get();
            notificationRepository.delete(result);
            return modelMapper.map(result, NotificationResponseDto.class);
        }
        return null;
    }

    @Override
    public NotificationResponseDto findById(Long id) {
        return modelMapper.map(notificationRepository.findById(id).get(), NotificationResponseDto.class);
    }

    @Override
    public List<NotificationResponseDto> findByUserId(Long userId) {
        if(!userRepository.findById(userId).isPresent()) {
            throw new InvalidRequestException(ErrorCode.NOT_JOINED_USER_ID);
        }

        List<Notification> notifications = notificationRepository.findByUserIdOrderByDateTimeDesc(userId);
        List<NotificationResponseDto> result = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationResponseDto r = modelMapper.map(notification, NotificationResponseDto.class);
            r.setUser(modelMapper.map(notification.getUser(), UserDto.class));
            result.add(r);
        }

        return result;
//        return null;
    }

    @Override
    public List<NotificationResponseDto> findByUserIdAndType(Long userId, Integer type) {
        if(!userRepository.findById(userId).isPresent()) {
            throw new InvalidRequestException(ErrorCode.NOT_JOINED_USER_ID);
        }

        List<Notification> notifications = notificationRepository.findByUserIdAndTypeOrderByDateTimeDesc(userId, type);
        List<NotificationResponseDto> result = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationResponseDto r = modelMapper.map(notification, NotificationResponseDto.class);
            r.setUser(modelMapper.map(notification.getUser(), UserDto.class));
            result.add(r);
        }

        return result;
//        return null;
    }
}
