package com.testcar.car.common.exception;


import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {
    public BadRequestException(BaseErrorCode errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }
}
