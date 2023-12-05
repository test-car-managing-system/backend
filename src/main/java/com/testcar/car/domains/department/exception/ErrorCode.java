package com.testcar.car.domains.department.exception;


import com.testcar.car.common.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements BaseErrorCode {
    DEPARTMENT_NOT_FOUND("DEP001", "해당 부서를 찾을 수 없습니다.");

    private final String code;
    private final String message;
}
