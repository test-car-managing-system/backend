package com.testcar.car.domains.carReservation.exception;


import com.testcar.car.common.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements BaseErrorCode {
    CAR_STOCK_NOT_AVAILABLE("CRS001", "해당 재고는 대여 불가합니다."),
    CAR_RESERVATION_NOT_FOUND("CRS002", "해당 예약 정보를 찾을 수 없습니다.");

    private final String code;
    private final String message;
}
