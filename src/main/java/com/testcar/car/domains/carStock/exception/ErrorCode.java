package com.testcar.car.domains.carStock.exception;


import com.testcar.car.common.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements BaseErrorCode {
    CAR_STOCK_NOT_FOUND("STK001", "해당 재고를 찾을 수 없습니다."),
    DUPLICATED_STOCK_NUMBER("STK002", "중복된 재고번호입니다."),
    CAR_STOCK_NOT_RESERVED("STK003", "예약중인 차량이 아닙니다.");

    private final String code;
    private final String message;
}
