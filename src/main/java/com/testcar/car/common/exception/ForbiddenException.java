package com.testcar.car.common.exception;


import org.springframework.http.HttpStatus;

public class ForbiddenException extends BaseException {
    public ForbiddenException(BaseErrorCode errorCode) {
        super(HttpStatus.FORBIDDEN, errorCode);
    }
}
