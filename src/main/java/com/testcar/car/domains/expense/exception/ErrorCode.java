package com.testcar.car.domains.expense.exception;


import com.testcar.car.common.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements BaseErrorCode {
    EXPENSE_NOT_FOUND("EXP001", "해당 지출 내역을 찾을 수 없습니다.");

    private final String code;
    private final String message;
}
