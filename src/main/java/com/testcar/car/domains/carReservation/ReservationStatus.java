package com.testcar.car.domains.carReservation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationStatus {
    AVAILABLE("대여 가능"),
    INSPECTION("검수중"),
    RESERVED("대여중"),
    UNAVAILABLE("폐기");

    private final String description;
}
