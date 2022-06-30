package com.ssafy.withssafy.errorcode;

public enum ErrorCode {
    INVALID_REQUEST("E0001"),
    FAILED_TO_SAVE_FILE("E0002"),
    JOINED_STUDY_USER("E0003"),
    JOINED_USER_ID("E0004"),
    NOT_JOINED_USER_ID("E0005"),
    DOESNT_EXIST("E0006"),
    ;

    private final String errorCode;

    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getCode() {
        return String.format("error.%s", errorCode);
    }
}
