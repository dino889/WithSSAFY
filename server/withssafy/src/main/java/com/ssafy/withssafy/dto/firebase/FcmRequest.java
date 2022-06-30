package com.ssafy.withssafy.dto.firebase;

import lombok.Data;

@Data
public class FcmRequest {
    private String title;
    private String body;
    private String img;
    private int type;
}
