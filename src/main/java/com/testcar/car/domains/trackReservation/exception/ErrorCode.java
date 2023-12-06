package com.testcar.car.domains.trackReservation.exception;


import com.testcar.car.common.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements BaseErrorCode {
    TRACK_RESERVATION_NOT_FOUND("RES001", "시험장 예약 정보를 찾을 수 없습니다."),
    INVALID_RESERVATION_SLOT("RES002", "예약 가능한 시간이 아닙니다."),
    INVALID_RESERVATION_TIME("RES003", "예약 시간은 하루 단위로 지정할 수 있습니다."),
    EMPTY_RESERVATION_SLOT("RES004", "예약 시간을 지정해야 합니다."),
    RESERVATION_ALREADY_CANCELED("RES005", "이미 취소된 예약입니다.");
    private final String code;
    private final String message;
}
