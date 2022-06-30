package com.ssafy.withssafy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_notice_type")
@Getter
@NoArgsConstructor
public class NoticeType {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String type;

    @Builder
    public NoticeType(Long id, String type) {
        this.id = id;
        this.type = type;
    }
}
