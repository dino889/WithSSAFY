package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "tbl_sb_comment")
@Getter
@NoArgsConstructor
public class SbComment {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sb_id")
    private StudyBoard studyBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private SbComment parent;

    @Column(length = 500)
    private String content;

    @Column
    private String write_dt;

    public void setParentId(Long id){
        if(id != null)
            parent = SbComment.builder().id(id).build();
    }

    @Builder
    public SbComment(Long id, StudyBoard studyBoard, User user, SbComment parent, String content, String write_dt){
        this.id = id;
        this.studyBoard = studyBoard;
        this.user = user;
        this.parent = parent;
        this.content = content;
        this.write_dt = write_dt;
    }

    public void updateStudyBoard(StudyBoard studyBoard){
        this.studyBoard = studyBoard;
    }
}