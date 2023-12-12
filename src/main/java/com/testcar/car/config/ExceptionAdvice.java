package com.testcar.car.config;

import static com.testcar.car.common.exception.ErrorCode.BAD_REQUEST;
import static com.testcar.car.common.exception.ErrorCode.INTERNAL_SERVER_ERROR;

import com.testcar.car.common.exception.BadRequestException;
import com.testcar.car.common.exception.BaseException;
import com.testcar.car.common.exception.InternalServerException;
import com.testcar.car.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** 전역 예외 처리를 하기 위한 핸들러 입니다 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
    /** Request 잘못된 입력값 바인딩에 대한 예외 처리 */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
            HttpMessageNotReadableException e) {
        log.warn("HttpMessageNotReadableException. error message: request field error", e);
        final BadRequestException exception = new BadRequestException(BAD_REQUEST);
        final ErrorResponse response = ErrorResponse.from(exception);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /** Request Param 필수 파라미터에 대한 예외 처리 */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {
        log.warn("MissingServletRequestParameterException. error message: request field error", e);
        final BadRequestException exception = new BadRequestException(BAD_REQUEST);
        final ErrorResponse response = ErrorResponse.from(exception);
        return new ResponseEntity<>(response, e.getStatusCode());
    }

    /** Request body 파라미터에 대한 예외 처리 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        log.warn("MethodArgumentNotValidException. error message: request field error", e);
        final BadRequestException exception = new BadRequestException(BAD_REQUEST);
        final ErrorResponse response = ErrorResponse.of(exception, e.getBindingResult());
        return new ResponseEntity<>(response, e.getStatusCode());
    }

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
