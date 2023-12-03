package com.testcar.car.common.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {
    private HttpStatus httpStatus;
    private String code;
    private String message;

    public BaseException(HttpStatus httpStatus, BaseErrorCode errorCode) {
        this.httpStatus = httpStatus;
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
