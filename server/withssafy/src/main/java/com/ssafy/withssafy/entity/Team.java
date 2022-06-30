package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_team")
@Getter
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private int minLimit;

    @Column
    private int maxLimit;

    @Column
    private int classification;

    @Column
    private String options;


    @Builder
    public Team(Long id, int minLimit, int maxLimit, int classification, String options){
        this.id = id;
        this.minLimit = minLimit;
        this.maxLimit = maxLimit;
        this.classification = classification;
        this.options = options;
    }
}
