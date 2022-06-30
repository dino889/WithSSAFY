package com.ssafy.withssafy.exception;

import com.ssafy.withssafy.errorcode.ErrorCode;
import com.ssafy.withssafy.util.MessageConverter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(ErrorCode errorCode) {
        super(MessageConverter.getMessage(errorCode));
    }

    public InvalidRequestException(ErrorCode errorCode, Throwable throwable) {
        super(MessageConverter.getMessage(errorCode), throwable);
    }
}
