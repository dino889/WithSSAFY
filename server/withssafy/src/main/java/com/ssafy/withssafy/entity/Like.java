package com.ssafy.withssafy.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tbl_like")
@Getter
@NoArgsConstructor

public class Like {
    @Id @GeneratedValue
    private Long id;

    @Column
    private byte isLiked;

    @Builder
    public Like(byte isLiked){
        this.isLiked = isLiked;
    }
}