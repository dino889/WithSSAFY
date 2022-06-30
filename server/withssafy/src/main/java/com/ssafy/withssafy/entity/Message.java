package com.ssafy.withssafy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_message")
@Getter
@NoArgsConstructor
public class Message {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "u_to_id")
    private User u_to;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "u_from_id")
    private User u_from;

    @Column
    private String content;

    @Column
    private LocalDateTime send_dt;

    @Builder
    public Message(Long id, User u_from, User u_to, String content, LocalDateTime send_dt){
        this.id = id;
        this.u_from = u_from;
        this.u_to = u_to;
        this.content = content;
        this.send_dt = send_dt;
    }
}