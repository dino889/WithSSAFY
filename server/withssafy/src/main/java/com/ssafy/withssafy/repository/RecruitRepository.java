package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecruitRepository extends JpaRepository<Recruit,Long> {

    @Query(value = "select r.* from tbl_recruit r, tbl_recruit_like_management m where m.user_id =:userId and m.recruit_id = r.id", nativeQuery = true)
    List<Recruit> findAllMyRecruit(@Param(value = "userId") Long userId);
}
