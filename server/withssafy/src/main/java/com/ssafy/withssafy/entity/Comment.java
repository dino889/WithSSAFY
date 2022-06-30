package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "tbl_comment")
@Getter

@NoArgsConstructor
public class Comment {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Column
    private String content;

    @Column
    private String write_dt;

    public void setParentId(Long id){
        if(id != null)
            parent = Comment.builder().id(id).build();
    }

    @Builder
    public Comment(Long id, Board board, User user, Comment parent, String content, String write_dt){
        this.id = id;
        this.board = board;
        this.user = user;
        this.parent = parent;
        this.content = content;
        this.write_dt = write_dt;
    }
}
