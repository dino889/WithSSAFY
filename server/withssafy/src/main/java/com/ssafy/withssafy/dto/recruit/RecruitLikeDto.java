package com.ssafy.withssafy.dto.recruit;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RecruitLikeDto {
    private Long id;
    @ApiModelProperty(example = "채용공고 id")
    private Long recruitId;
    @ApiModelProperty(example = "유저 id")
    private Long userId;
}
