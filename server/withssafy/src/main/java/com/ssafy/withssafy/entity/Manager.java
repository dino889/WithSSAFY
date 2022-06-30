package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_manager")
@Getter
@NoArgsConstructor
public class Manager {
    @Id @GeneratedValue
    private Long id;

    @Column
    private int auth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Manager(Long id, int auth, User user){
        this.id = id;
        this.auth = auth;
        this.user = user;
    }
}
