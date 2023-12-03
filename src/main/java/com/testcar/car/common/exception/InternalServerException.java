package com.testcar.car.common.exception;


import org.springframework.http.HttpStatus;

public class InternalServerException extends BaseException {
    public InternalServerException(BaseErrorCode errorCode) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, errorCode);
    }
}
