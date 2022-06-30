package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tbl_team_member")
@Getter
@Setter
@NoArgsConstructor
public class TeamMember {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tb_id")
    private TeamBuilding tb_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user_id;

    @Column
    private int position;

    @Builder
    public TeamMember(TeamBuilding tb_id, User user_id, int position){
        this.tb_id = tb_id;
        this.user_id = user_id;
        this.position = position;
    }
}