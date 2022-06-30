package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.SbComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface SbCommentRepository extends JpaRepository<SbComment,Long> {
    @Query(value = "SELECT * FROM tbl_sb_comment WHERE user_id=:userId", nativeQuery = true)
    List<SbComment> findByUserId(Long userId);

    @Query(value = "SELECT * FROM tbl_sb_comment WHERE sb_id=:boardId", nativeQuery = true)
    List<SbComment> findByBoardId(Long boardId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE tbl_sb_comment SET content=:content WHERE id=:id", nativeQuery = true)
    void update(Long id, String content);
}
