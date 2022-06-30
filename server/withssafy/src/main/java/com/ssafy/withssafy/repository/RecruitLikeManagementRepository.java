package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.RecruitLikeManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RecruitLikeManagementRepository extends JpaRepository<RecruitLikeManagement,Long> {
    RecruitLikeManagement findByRecruitIdAndUserId(Long recruitId, Long userId);
    @Transactional
    @Modifying
    @Query(value = "delete from tbl_recruit_like_management where recruit_id = :id", nativeQuery = true)
    int deleteByRecruitId(Long id);
}
