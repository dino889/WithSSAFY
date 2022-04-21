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
public class RecruitListResDto {

    private Long id;
    @ApiModelProperty(example = "유저 id ex)243")
    private Long userId;
    @ApiModelProperty(example = "기업명")
    private String company;
    @ApiModelProperty(example = "경력or신입")
    private String career;
    @ApiModelProperty(example = "학력")
    private String education;
    @ApiModelProperty(example = "직무")
    private String job;
    @ApiModelProperty(example = "고용형태 ex)정규직")
    private String employType;
    @ApiModelProperty(example = "급여")
    private String salary;
    @ApiModelProperty(example = "근무지")
    private String location;
    @ApiModelProperty(example = "2020-01-01")
    private String startDate;
    @ApiModelProperty(example = "2020-01-05")
    private String endDate;
}
