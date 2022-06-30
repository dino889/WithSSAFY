package com.ssafy.withssafy.entity;

import com.ssafy.withssafy.dto.board.BoardTypeRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_board_type")
@Getter
@NoArgsConstructor

public class BoardType {
    @Id @GeneratedValue
    private Long id;

    @Column
    private String type;

    public void updateBoardType(BoardTypeRequest boardTypeRequest){
        this.type = boardTypeRequest.getType();
    }


    @Builder
    public BoardType(Long id, String type){
        this.id = id;
        this.type = type;
    }
}
