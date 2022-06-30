package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.Comment;
import com.ssafy.withssafy.entity.Report;
import com.ssafy.withssafy.entity.SbComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Long> {

//    @Query(value = "SELECT * FROM tbl_report WHERE board_id=:boardId",nativeQuery = true)
    List<Report> findByBoardId(@Param("boardId") Long board);

//    @Query(value = "SELECT * FROM tbl_report WHERE comment_id=:commentId",nativeQuery = true)
    List<Report> findByCommentId(@Param("commentId")Long comment);
}
