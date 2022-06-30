package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query(value = "SELECT * FROM tbl_comment WHERE board_id=:boardId", nativeQuery = true)
    List<Comment> findByBoardId(@Param(value = "boardId") Long boardId);

    @Query(value = "SELECT * FROM tbl_comment WHERE user_id=:userId", nativeQuery = true)
    List<Comment> findByUserId(@Param(value = "userId") Long userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE tbl_comment SET content=:content WHERE id=:id", nativeQuery = true)
    void update(@Param(value = "id") Long id, @Param(value = "content") String content);
}
