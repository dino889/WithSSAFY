package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.LikeManagement;
import com.ssafy.withssafy.entity.RecruitLikeManagement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeManagementRepository extends JpaRepository<LikeManagement,Long> {
    LikeManagement findByBoardIdAndUserId(Long boardId, Long userId);
}
