package com.ssafy.withssafy.entity;

import com.ssafy.withssafy.dto.schedule.ScheduleDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "tbl_schedule")
@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classRoom_id")
    private ClassRoom classRoom;

    @Column
    private LocalDateTime endDate;

    @Column(length = 50)
    private String title;

    @Column(length = 250)
    private String memo;

    @Column
    private LocalDateTime startDate;

    @Column
    private int weeks;

    public Schedule(Long id, User user, ClassRoom classRoom, LocalDateTime endDate, String title, String memo, LocalDateTime startDate, int weeks) {
        this.id = id;
        this.user = user;
        this.classRoom = classRoom;
        this.endDate = endDate;
        this.title = title;
        this.memo = memo;
        this.startDate = startDate;
        this.weeks = weeks;
    }
}
