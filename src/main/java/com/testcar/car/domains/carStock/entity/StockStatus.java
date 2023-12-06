package com.testcar.car.domains.carStock.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StockStatus {
    AVAILABLE("대여 가능"),
    INSPECTION("검수중"),
    RESERVED("대여중"),
    UNAVAILABLE("폐기");

    private final String description;
}
