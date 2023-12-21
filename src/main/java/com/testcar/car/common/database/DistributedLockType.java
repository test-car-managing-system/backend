package com.testcar.car.common.database;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DistributedLockType {
    TRACK("track"),
    CAR("car");

    private final String lockName;
}
