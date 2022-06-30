package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.Like;
import com.ssafy.withssafy.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager,Long> {
    @Query(value = "SELECT * FROM tbl_manager WHERE user_id=:user_id", nativeQuery = true)
    Manager findByUId(@Param("user_id") Long user_id);

    Optional<Manager> findByUserId(Long userId);
}
