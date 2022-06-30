package com.ssafy.withssafy.dto.recruit;

import com.ssafy.withssafy.entity.Recruit;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RecruitDto {

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
    @ApiModelProperty(example = "담당업무")
    private String taskDescription;
    @ApiModelProperty(example = "우대사항")
    private String preferenceDescription;
    @ApiModelProperty(example = "복리후생")
    private String welfare;
    @ApiModelProperty(example = "근무시간")
    private String workingHours;
    @ApiModelProperty(example = "2020-01-01")
    private String startDate;
    @ApiModelProperty(example = "2020-01-05")
    private String endDate;
    @ApiModelProperty(example = "이미지 경로")
    private String photoPath;

}
