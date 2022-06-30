package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_report")
@Getter
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String content;

    @Column
    private String write_dt;

    @Builder
    public Report(Long id, Board board , Comment comment, User user, String content, String write_dt){
        this.id = id;
        this.board = board;
        this.comment = comment;
        this.user = user;
        this.content = content;
        this.write_dt = write_dt;
    }

    public void setReport(Board board, Comment comment, User user){
        this.board = board;
        this.comment = comment;
        this.user = user;
    }
}
