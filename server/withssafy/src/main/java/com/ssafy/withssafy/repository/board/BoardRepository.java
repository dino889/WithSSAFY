package com.ssafy.withssafy.repository.board;

import com.ssafy.withssafy.entity.Board;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository {
    List<Board> findAllByTypeId(Long typeId, Sort sort);

    @Query("SELECT board FROM Board board WHERE board.likes.size >= 10 ORDER BY board.likes.size DESC")
    List<Board> findAllHotBoards(Sort sort);

    @Query("SELECT board " +
            "FROM Board board LEFT JOIN " +
            "LikeManagement likeManagement " +
            "ON board.id = likeManagement.board.id " +
            "WHERE likeManagement.user.id = :userId")
    List<Board> findAllLikedBoardByUserId(@Param(value = "userId") Long userId, Sort sort);
}
