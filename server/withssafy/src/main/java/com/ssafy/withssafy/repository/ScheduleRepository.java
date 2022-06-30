package com.ssafy.withssafy.repository;

import com.ssafy.withssafy.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    List<Schedule> findAllByClassRoomIdAndStartDateBetween(Long classRoomId, LocalDateTime fromDate, LocalDateTime toDate);
//    List<Schedule> findAllByClassRoomIdAndWeeks(Long classRoomId, int weeks);
    List<Schedule> findAllByClassRoomIdOrderByStartDate(Long classRoomId);

    @Query(value = "select s.* from tbl_schedule s\n" +
            "where s.class_room_id in (select c2.id\n" +
            "from tbl_classroom c1, tbl_classroom c2\n" +
            "where c1.id = :classRoomId and c1.generation = c2.generation and\n" +
            "c2.class_description = \"전체\" and (c1.area = c2.area or c2.area = \"전체\"));",nativeQuery = true)
    List<Schedule> findAllGenerationNotice(Long classRoomId);

    @Query(value = "select s.* from tbl_schedule s\n" +
            "where s.class_room_id in (select c2.id\n" +
            "from tbl_classroom c1, tbl_classroom c2\n" +
            "where c1.id = :id and c1.generation = c2.generation and\n" +
            "c2.class_description = \"전체\" and (c1.area = c2.area or c2.area = \"전체\"))\n" +
            "and date_format(s.start_date,'%Y-%m-%d') = :day",nativeQuery = true)
    List<Schedule> findAllGenerationNoticyByDay(Long id, LocalDate day);
}
