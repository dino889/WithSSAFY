package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
}
