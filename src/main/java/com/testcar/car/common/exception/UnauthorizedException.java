package com.testcar.car.common.exception;


import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException(BaseErrorCode errorCode) {
        super(HttpStatus.UNAUTHORIZED, errorCode);
    }
}
