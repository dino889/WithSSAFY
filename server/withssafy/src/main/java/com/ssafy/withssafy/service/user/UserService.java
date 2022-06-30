package com.ssafy.withssafy.service.user;

import com.ssafy.withssafy.dto.classroom.ClassRoomDto;
import com.ssafy.withssafy.dto.manager.ManagerDto;
import com.ssafy.withssafy.dto.user.LoginDto;
import com.ssafy.withssafy.dto.user.UserAuthResponse;
import com.ssafy.withssafy.dto.user.UserDto;
import com.ssafy.withssafy.entity.*;
import com.ssafy.withssafy.errorcode.ErrorCode;
import com.ssafy.withssafy.exception.InvalidRequestException;
import com.ssafy.withssafy.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final ClassRoomRepository classRoomRepository;
    private final ManagerRepository managerRepository;
    private final ClassManagerRepository classManagerRepository;
    private final ModelMapper modelMapper;

    /**
     * 새로운 사용자를 등록한다.
     *
     * @param userDto
     * @return user
     */
    public UserDto insertUser(UserDto userDto) {
        if (userRepository.findByUid(userDto.getUserId()).isPresent()) {
            throw new InvalidRequestException(ErrorCode.JOINED_USER_ID);
        }

        User user = modelMapper.map(userDto, User.class);
        User result = userRepository.save(user);
        return modelMapper.map(result, UserDto.class);
    }

    /**
     * 해당 이메일을 가진 사용자를 삭제한다.
     *
     * @param id
     * @return 해당 이메일을 가진 사용자가 있다면 삭제 후 true, 없다면 false
     */
    public Boolean deleteByUid(Long id) {
        if (!userRepository.findById(id).isPresent()) return false;
        userRepository.deleteById(id);
        return true;
    }

    /**
     * 해당 사용자의 아이디를 가진 유저의 비밀번호를 수정한다.
     *
     * @param id
     * @param password
     * @return 해당 사용자 User 정보
     */
    public UserDto updatePasswordByUid(Long id, String password) {
        if (!userRepository.findById(id).isPresent()) return null;
        userRepository.updatePasswordById(id, password);
        User user = userRepository.findById(id).get();
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto findById(Long u_id) {
        if (!userRepository.findById(u_id).isPresent()) return null;

        User user = userRepository.findById(u_id).get();
        return modelMapper.map(user, UserDto.class);
    }

    /**
     * 해당 아이디를 가진 사용자를 조회한다.
     *
     * @param userId
     * @return 해당 사용자 User
     */
    public UserDto findByUid(String userId) {

        User user = userRepository.findByUid(userId).get();
        return modelMapper.map(user, UserDto.class);
    }

    /**
     * 전체 사용자를 조회한다.
     *
     * @return List<User>
     */
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }


    /**
     * 비밀번호와 아이디를 통해 사용자를 조회한다.
     *
     * @param userId
     * @param password
     * @return User
     */
    public LoginDto login(String userId, String password) {
        User user = userRepository.login(userId, password);
        LoginDto result = modelMapper.map(user, LoginDto.class);
        result.setClassRoomDto(modelMapper.map(user.getClassRoom(), ClassRoomDto.class));
        Manager manager = managerRepository.findByUId(user.getId());
        if (manager != null) result.setManagerDto(modelMapper.map(manager, ManagerDto.class));
        return result;
    }

    public UserDto updateClassById(Long id, Long classId) {
        if (!userRepository.findById(id).isPresent()) return null;
        userRepository.updateClassById(id, classId);
        User user = userRepository.findById(id).get();
        return modelMapper.map(user, UserDto.class);
    }

    public LoginDto insertManager(UserDto userDto, int status) {
        if (userRepository.findByUid(userDto.getUserId()).isPresent()) {
            throw new InvalidRequestException(ErrorCode.JOINED_USER_ID);
        }

        User user = userRepository.save(modelMapper.map(userDto, User.class));
        Manager manager = managerRepository.save(new Manager(0L, status, user));
        if (status != 0 && userDto.getClassRoomId() != null)
            classManagerRepository.save(new ClassManager(0L, user.getClassRoom(), user));

        LoginDto result = modelMapper.map(user, LoginDto.class);
        if (user.getClassRoom() != null)
            result.setClassRoomDto(modelMapper.map(user.getClassRoom(), ClassRoomDto.class));
        result.setManagerDto(modelMapper.map(manager, ManagerDto.class));
        return result;
    }

    @Modifying(clearAutomatically = true)
    public UserDto updateState(Long id, int state) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            user.get().updateState(state);
        } else {
            throw new InvalidRequestException(ErrorCode.NOT_JOINED_USER_ID);
        }

        return modelMapper.map(user.get(), UserDto.class);
    }

    @Modifying(clearAutomatically = true)
    public UserDto updateDeviceToken(Long id, String token) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            List<User> list = userRepository.findUsersByDeviceToken(token);
            for(User u : list){
                u.updateDeviceToken(null);
            }

            user.get().updateDeviceToken(token);
        } else {
            throw new InvalidRequestException(ErrorCode.NOT_JOINED_USER_ID);
        }
        return modelMapper.map(user.get(), UserDto.class);
    }


    public List<UserDto> findStateZero() {
        List<User> list = userRepository.findByState(0);
        return list.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    public UserAuthResponse findAuthByUserId(Long userId) {
        Optional<Manager> manager = managerRepository.findByUserId(userId);

        if (manager.isPresent()) {
            Optional<UserAuthResponse> userAuthResponse = manager.map(value -> modelMapper.map(value, UserAuthResponse.class));
            if(userAuthResponse.isPresent())
                return userAuthResponse.get();
        }

        throw new InvalidRequestException(ErrorCode.INVALID_REQUEST);
    }

    public UserDto findByDeviceToken(String token) {
        return modelMapper.map(userRepository.findByDeviceToken(token), UserDto.class);
    }
}
