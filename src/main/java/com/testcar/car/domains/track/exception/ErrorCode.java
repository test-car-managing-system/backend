package com.testcar.car.domains.track.exception;


import com.testcar.car.common.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements BaseErrorCode {
    TRACK_NOT_FOUND("TRK001", "해당 시험장을 찾을 수 없습니다."),
    DUPLICATED_TRACK_NAME("TRK002", "중복된 시험장명입니다."),
    ;
    private final String code;
    private final String message;
}
