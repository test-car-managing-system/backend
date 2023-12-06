package com.testcar.car.domains.carTest.exception;


import com.testcar.car.common.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements BaseErrorCode {
    CAR_TEST_NOT_FOUND("TST001", "해당 시험 결과를 찾을 수 없습니다.");
    private final String code;
    private final String message;
}
