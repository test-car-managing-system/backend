package com.testcar.car.domains.gasStation.exception;


import com.testcar.car.common.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements BaseErrorCode {
    GAS_STATION_NOT_FOUND("GAS001", "해당 주유소를 찾을 수 없습니다.");
    private final String code;
    private final String message;
}
