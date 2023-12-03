package com.testcar.car.config;

import static com.testcar.car.common.exception.ErrorCode.INTERNAL_SERVER_ERROR;

import com.testcar.car.common.exception.BaseException;
import com.testcar.car.common.exception.InternalServerException;
import com.testcar.car.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** 전역 예외 처리를 하기 위한 핸들러 입니다 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleBaseException(BaseException e) {
        log.error("handleBaseException", e);
        final ErrorResponse response = ErrorResponse.from(e);
        return new ResponseEntity<>(response, e.getHttpStatus());
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleAllException(Exception e) {
        log.error("Exception has occurred ", e);
        final InternalServerException exception =
                new InternalServerException(INTERNAL_SERVER_ERROR);
        final ErrorResponse response = ErrorResponse.from(exception);
        return new ResponseEntity<>(response, exception.getHttpStatus());
    }
}
