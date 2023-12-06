package com.testcar.car.domains.gasStationHistory.exception;


import com.testcar.car.common.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements BaseErrorCode {
    GAS_STATION_HISTORY_NOT_FOUND("GSS001", "해당 주유 이력을 찾을 수 없습니다."),
    ;
    private final String code;
    private final String message;
}
