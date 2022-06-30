package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByClassRoomIdOrderByWriteDtDesc(Long id);
}
