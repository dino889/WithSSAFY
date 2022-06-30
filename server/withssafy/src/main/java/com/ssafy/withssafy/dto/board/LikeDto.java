package com.ssafy.withssafy.dto.board;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LikeDto {
    private Long id;
    @ApiModelProperty(example = "board id")
    private Long boardId;
    @ApiModelProperty(example = "유저 id")
    private Long userId;
}
