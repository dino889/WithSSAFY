package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.StudyBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyBoardRepository extends JpaRepository<StudyBoard,Long> {
    List<StudyBoard> findByType(int type);
    List<StudyBoard> findByAreaAndType(String area, int type);
}
