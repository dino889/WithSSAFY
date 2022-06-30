package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tbl_notification")
@Getter
@Setter
@NoArgsConstructor
public class Notification {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private int type;

    @Column(length = 25)
    private String dateTime;

    @Column(length = 50)
    private String title;

    @Column(length = 250)
    private String content;

    @Builder
    public Notification(User user, int type, String dateTime, String title, String content){
        this.user = user;
        this.type = type;
        this.dateTime = dateTime;
        this.title = title;
        this.content = content;
    }
}