package com.testcar.car.domains.trackReservation.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationStatus {
    RESERVED("예약완료"),
    USING("사용중"),
    CANCELED("예약취소"),
    COMPLETED("이용완료");

    private final String description;
}
