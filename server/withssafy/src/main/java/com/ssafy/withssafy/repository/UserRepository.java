package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value = "SELECT * FROM tbl_user WHERE user_id=:userId", nativeQuery = true)
    Optional<User> findByUid(@Param("userId") String userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE tbl_user SET password=:password WHERE id=:id", nativeQuery = true)
    void updatePasswordById(@Param("id")Long id, @Param("password")String password);

    @Query(value = "SELECT * FROM tbl_user WHERE user_id=:userId AND password=:password", nativeQuery = true)
    User login(@Param("userId") String userId, @Param("password") String password);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE tbl_user SET classroom_id=:classId WHERE id=:id", nativeQuery = true)
    void updateClassById(Long id, Long classId);

    List<User> findByState(@Param("state") int state);

    User findByDeviceToken(String token);

    @Query(value = "SELECT * FROM tbl_user WHERE device_token=:token", nativeQuery = true)
    List<User> findUsersByDeviceToken(String token);
}
