package com.ssafy.withssafy.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "tbl_class_manager")
@Getter
@NoArgsConstructor
public class ClassManager {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    ClassRoom classRoom;

    public ClassManager(long id, ClassRoom classRoom, User user) {
        this.id = id;
        this.classRoom = classRoom;
        this.user = user;
    }
}
