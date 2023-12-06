package com.testcar.car.domains.carReservation.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationStatus {
    RESERVED("대여중"),
    RETURNED("반납완료");

    private final String description;
}
