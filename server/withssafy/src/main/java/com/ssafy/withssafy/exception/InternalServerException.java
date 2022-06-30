package com.ssafy.withssafy.exception;

import com.ssafy.withssafy.errorcode.ErrorCode;
import com.ssafy.withssafy.util.MessageConverter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends RuntimeException {
    public InternalServerException(ErrorCode errorCode) {
        super(MessageConverter.getMessage(errorCode));
    }

    public InternalServerException(ErrorCode errorCode, Throwable throwable) {
        super(MessageConverter.getMessage(errorCode), throwable);
    }
}
