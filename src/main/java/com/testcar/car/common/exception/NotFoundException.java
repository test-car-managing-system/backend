package com.testcar.car.common.exception;


import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {
    public NotFoundException(BaseErrorCode errorCode) {
        super(HttpStatus.NOT_FOUND, errorCode);
    }
}
