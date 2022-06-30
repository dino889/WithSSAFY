package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "tbl_team_building")
@Getter
@Setter
@NoArgsConstructor
public class TeamBuilding {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 50)
    private String title;

    @Column(length = 500)
    private String content;

    @Column(length = 500)
    private String photo_path;

    @Column(length = 500, name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column
    private int tb_limit;

    @Builder
    public TeamBuilding(User user, String title, String content, String photo_path,LocalDateTime createdAt, int tb_limit){
        this.user = user;
        this.title = title;
        this.content = content;
        this.photo_path = photo_path;
        this.createdAt = createdAt;
        this.tb_limit = tb_limit;

    }
}