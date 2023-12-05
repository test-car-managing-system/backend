package com.testcar.car.domains.car.exception;


import com.testcar.car.common.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements BaseErrorCode {
    CAR_NOT_FOUND("CAR001", "해당 차량을 찾을 수 없습니다."),
    DUPLICATED_CAR_NAME("CAR002", "중복된 차량명입니다."),
    ;
    private final String code;
    private final String message;
}
