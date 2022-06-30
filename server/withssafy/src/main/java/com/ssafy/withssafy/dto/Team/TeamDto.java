package com.ssafy.withssafy.dto.Team;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TeamDto {
    private Long id;
    private int minLimit;
    private int maxLimit;
    private int classification;
    private String options;
}
