package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tbl_tb_comment")
@Getter
@Setter
@NoArgsConstructor
public class TbComment {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tb_id")
    private TeamBuilding tb_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user_id;

    @Column
    private int parent;

    @Column(length = 500)
    private String content;

    @Builder
    public TbComment(TeamBuilding tb_id, User user_id, int parent, String content){
        this.tb_id = tb_id;
        this.user_id = user_id;
        this.parent = parent;
        this.content = content;
    }
}