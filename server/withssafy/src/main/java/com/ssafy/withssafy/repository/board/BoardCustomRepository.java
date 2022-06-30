package com.ssafy.withssafy.repository.board;

import com.ssafy.withssafy.entity.Board;

import java.util.List;

public interface BoardCustomRepository {
    List<Board> findByDynamicQuery(Long boardType, Long userId);

    List<Board> findByComment(Long userId);
}
