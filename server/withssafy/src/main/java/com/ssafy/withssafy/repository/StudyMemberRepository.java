package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.dto.studyboard.StudyMemberRequest;
import com.ssafy.withssafy.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface StudyMemberRepository extends JpaRepository<StudyMember,Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM StudyMember WHERE sb_id = :#{#studyMember.studyBoardId} AND user_id = :#{#studyMember.userId}")
    void deleteBySbIdAndUserId(@Param(value = "studyMember") StudyMemberRequest studyMemberRequest);

    @Query(value = "SELECT sm FROM StudyMember sm WHERE sb_id = :#{#studyMember.studyBoardId} AND user_id = :#{#studyMember.userId}")
    Optional<StudyMember> findBySbIdAndUserId(@Param(value = "studyMember") StudyMemberRequest studyMemberRequest);
}
