package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_classroom")
@Getter
@NoArgsConstructor
public class ClassRoom {
    @Id @GeneratedValue
    private Long id;

    @Column
    private int generation;

    @Column(length = 20)
    private String area;

    @Column(length = 100)
    private String classDescription;

    @Builder
    public ClassRoom(Long id, int generation, String area, String classDescription){
        this.id = id;
        this.generation = generation;
        this.area = area;
        this.classDescription = classDescription;
    }
}
