package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tbl_study_member")
@Getter
@NoArgsConstructor
public class StudyMember {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sb_id")
    private StudyBoard studyBoard;

    @Builder
    public StudyMember(StudyBoard studyBoard, User user){
        this.studyBoard = studyBoard;
        this.user = user;
    }
}